import './sign-up.scss';
import { useState } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import { postData, validateStringFields, validatePasswordField, validateEmailField, validatePhoneField, validateBuildingNumberField } from './../../services/services';

import FormInput from './../form-input/form-input.jsx';
import CustomButton from './../button/custom-button';

const SignUp = () => {

    const [passwordShown, setPasswordShown] = useState(false);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [dob, setDob] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [country, setCountry] = useState('');
    const [city, setCity] = useState('');
    const [street, setStreet] = useState('');
    const [buildingNumber, setBuildingNumber] = useState('');

    const [errorFirstName, setErrorFirstName] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorLastName, setErrorLastName] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorDob, setErrorDob] = useState({errorState: false, messagge: " (Nie określono daty)"});
    const [errorPhone, setErrorPhone] = useState({errorState: false, messagge: " (Musi zaczynać się od +48...)"});
    const [errorEmail, setErrorEmail] = useState({errorState: false, messagge: " (Musi być w postaci *@*.*)"});
    const [errorPassword, setErrorPassword] = useState({errorState: false, messagge: " (Musi zawierać co najmniej 6 znaków)"});
    const [errorCountry, setErrorCountry] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorCity, setErrorCity] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorStreet, setErrorStreet] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorBuildingNumber, setErrorBuildingNumber] = useState({errorState: false, messagge: " (Musi zawierać tylko cyfry)"});


    const eye = <FontAwesomeIcon icon={faEye} />;
    const eyeSlash = <FontAwesomeIcon icon={faEyeSlash} />;

    const validateFields = () => {
        if (!validateStringFields(firstName)) {
            setErrorFirstName({...errorFirstName, errorState : true});
        }
        if (!validateStringFields(lastName)) {
            setErrorLastName({...errorLastName, errorState : true});
        }
        if (!validateStringFields(country)) {
            setErrorCountry({...errorCountry, errorState : true});
        }
        if (!validateStringFields(city)) {
            setErrorCity({...errorCity, errorState : true});
        }
        if (!validateStringFields(street)) {
            setErrorStreet({...errorStreet, errorState : true});
        }
        if (!validatePasswordField(password)) {
            setErrorPassword({...errorPassword, errorState : true});
        }
        if (!validateEmailField(email)) {
            setErrorEmail({...errorEmail, errorState : true});
        }
        if (!validatePhoneField(phone)) {
            setErrorPhone({...errorPhone, errorState : true});
        }
        if (!validateBuildingNumberField(buildingNumber)) {
            setErrorBuildingNumber({...errorBuildingNumber, errorState : true});
        }
    }

    const togglePasswordVisiblity = () => {
        setPasswordShown(passwordShown ? false : true);
    };

    const handleChange = (event, setter) => {
        const {value} = event.target;
        setter(value);
    }

    const clearErrorAfterFocus = (value, setter) => {
        setter({...value, errorState: false});
    }



    const handleSubmit = (event) => {
        event.preventDefault();

        const data = {
            firstName
        }

        postData("http://localhost:8080/subscribers", data)
            .then(res => {
                validateFields();
                console.log(res)
            });
            // .finally(() => {
            //     document.getElementById("create-subscriber-form").reset();
            // });
    }

    return (
        <div className="sign-up-wrapper">
            <form id="create-subscriber-form" method="post" onSubmit={handleSubmit}>
                <FormInput
                    handleChange={(e) => handleChange(e, setFirstName)}
                    clearError={() => clearErrorAfterFocus(errorFirstName, setErrorFirstName)}
                    error={errorFirstName}
                    name="firstName"
                    type="text"
                    label="Imię"
                    value={firstName}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setLastName)}
                    clearError={() => clearErrorAfterFocus(errorLastName ,setErrorLastName)}
                    error={errorLastName}
                    name="lastName"
                    type="text"
                    label="Nazwisko"
                    value={lastName}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setDob)}
                    clearError={() => clearErrorAfterFocus(errorDob ,setErrorDob)}
                    error={errorDob}
                    name="dob"
                    type="date"
                    label="Data narodzenia"
                    value={dob}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setPhone)}
                    clearError={() => clearErrorAfterFocus(errorPhone ,setErrorPhone)}
                    error={errorPhone}
                    name="phone"
                    type="text"
                    label="Numer telefonu"
                    value={phone}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setEmail)}
                    clearError={() => clearErrorAfterFocus(errorEmail , setErrorEmail)}
                    error={errorEmail}
                    name="email"
                    type="text"
                    label="E-mail"
                    value={email}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setPassword)}
                    clearError={() => clearErrorAfterFocus(errorPassword ,setErrorPassword)}
                    error={errorPassword}
                    name="password"
                    type={passwordShown ? "text" : "password"}
                    label="Hasło"
                    value={password}
                    i={<i onClick={togglePasswordVisiblity}>{passwordShown ? eyeSlash : eye}</i>}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setCountry)}
                    clearError={() => clearErrorAfterFocus(errorCountry ,setErrorCountry)}
                    error={errorCountry}
                    name="country"
                    type="text"
                    label="Kraj"
                    value={country}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setCity)}
                    clearError={() => clearErrorAfterFocus(errorCity ,setErrorCity)}
                    error={errorCity}
                    name="city"
                    type="text"
                    label="Miasto"
                    value={city}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setStreet)}
                    clearError={() => clearErrorAfterFocus(errorStreet ,setErrorStreet)}
                    error={errorStreet}
                    name="street"
                    type="text"
                    label="Ulica"
                    value={street}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setBuildingNumber)}
                    clearError={() => clearErrorAfterFocus(errorBuildingNumber ,setErrorBuildingNumber)}
                    error={errorBuildingNumber}
                    name="buildingNumber"
                    type="text"
                    label="Numer budynku"
                    value={buildingNumber}
                    required
                />

                <CustomButton 
                    type="submit"
                    additionalClass="submit"
                >
                    Stworzyć konto
                </CustomButton>
            </form>
        </div>
    );
}

export default SignUp;