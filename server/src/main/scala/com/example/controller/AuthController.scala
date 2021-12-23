package com.example.controller

import com.example.exception.{BuildExceptionHandler, EmailAlreadyExistException, NoSuchAddressException, NoSuchStreetException}
import com.example.model.{EditSubscriber, LoginSubscriber, NewSubscriber}
import com.example.payload.response.{BuildOkResponse, JwtResponse}
import com.example.repository.{RoleRepository, SubscriberDataRepository, SubscriberRepository}
import com.example.security.jwt.JwtUtils
import com.example.security.services.UserDetailsImpl
import com.example.service.NewSubscriberService
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.{CrossOrigin, ExceptionHandler, PostMapping, PutMapping, RequestBody, RequestMapping, RestController}

import javax.validation.{ConstraintViolationException, Valid}


@CrossOrigin(origins = Array("*"), maxAge = 3600)
@RestController
@RequestMapping(Array("/auth"))
class AuthController {

    @Autowired val authenticationManager: AuthenticationManager = null

    @Autowired val newSubscriberService: NewSubscriberService = null

    @Autowired val userRepository: SubscriberDataRepository = null

    @Autowired val subscriberRepository: SubscriberRepository = null

    @Autowired val roleRepository: RoleRepository = null

    @Autowired val encoder: PasswordEncoder = null

    @Autowired val jwtUtils: JwtUtils = null

    @PostMapping(Array("/signin"))
    def authenticateUser(@Valid @RequestBody loginSubscriber: LoginSubscriber): ResponseEntity[_] = {
        val authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginSubscriber.getEmail, loginSubscriber.getPassword))
        SecurityContextHolder.getContext.setAuthentication(authentication)
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.getPrincipal.asInstanceOf[UserDetailsImpl]
        val userCategories = newSubscriberService.getUserCategory(userDetails.getSubscriber)
        ResponseEntity.ok(new JwtResponse(
            jwt,
            userDetails.getId,
            userDetails.getPhone,
            userDetails.getCountry,
            userDetails.getCity,
            userDetails.getStreet,
            userDetails.getBuildingNumber,
            userDetails.getLatitude,
            userDetails.getLongitude,
            userDetails.getFirstName,
            userDetails.getLastName,
            userDetails.getEmail,
            userCategories)
        )
    }

    @PostMapping(Array("/signup"))
    def registerUser(@Valid @RequestBody newSubscriber: NewSubscriber): ResponseEntity[_] = {
        val location = newSubscriberService.getSubscriberLocation(newSubscriber)
        newSubscriberService.registerSubscriber(newSubscriber, location)
        val subscriberId: Int = userRepository.findByEmail(newSubscriber.getEmail).get.getId
        newSubscriberService.saveSubscriberCategory(subscriberId, newSubscriber.getCategory)
        BuildOkResponse.createOkResponse
    }

    @PutMapping(Array("/edit"))
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    def editUser(@Valid @RequestBody editSubscriber: EditSubscriber): ResponseEntity[_] = {
        val location = newSubscriberService.getSubscriberLocation(
            NewSubscriber(
                editSubscriber.getFirstName,
                editSubscriber.getLastName,
                editSubscriber.getPhone,
                editSubscriber.getEmail,
                editSubscriber.getOldPassword,
                editSubscriber.getCountry,
                editSubscriber.getCity,
                editSubscriber.getAddress,
                editSubscriber.getCategory,
                editSubscriber.getRole
            )
        )
        newSubscriberService.editUser(editSubscriber, location)
        val subscriberId: Int = userRepository.findByEmail(editSubscriber.getEmail).get.getId
        newSubscriberService.saveSubscriberCategory(subscriberId, editSubscriber.getCategory)

        BuildOkResponse.createOkResponse
    }

    @ExceptionHandler(Array(classOf[ConstraintViolationException]))
    def handleSubscriberException(constraintViolationException : ConstraintViolationException): ResponseEntity[_] = {
        implicit val formats = Serialization.formats(NoTypeHints)
        val violations = constraintViolationException.getConstraintViolations
        var errorMap: Map[String, Any] = Map[String, Any]()
        if (!violations.isEmpty) {
            violations.forEach(violation => errorMap = errorMap + (violation.getPropertyPath.toString -> violation.getMessage)
            )
        }
        errorMap = errorMap + ("status" -> HttpStatus.NOT_FOUND.value)
        val errorMessage = Serialization.write(errorMap)
        new ResponseEntity[AnyRef](errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(Array(classOf[EmailAlreadyExistException]))
    def handleEmailException(exception: EmailAlreadyExistException):ResponseEntity[_] = {
        val message = BuildExceptionHandler.createErrorMessage(exception)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Array(classOf[NoSuchAddressException]))
    def handleAddressException(exception: NoSuchAddressException):ResponseEntity[_] = {
        val message = BuildExceptionHandler.createErrorMessage(exception)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Array(classOf[NoSuchStreetException]))
    def handleStreetException(exception: NoSuchStreetException):ResponseEntity[_] = {
        val message = BuildExceptionHandler.createErrorMessage(exception)
        new ResponseEntity[AnyRef](message, HttpStatus.BAD_REQUEST)
    }
}
