package com.example.model

import javax.validation.constraints.NotBlank
import scala.beans.BeanProperty


class LoginSubscriber {

    @NotBlank
    @BeanProperty
    var email: String = _

    @NotBlank
    @BeanProperty
    var password: String = _

}
