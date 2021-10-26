import './sign-up.scss';
import { useState, useEffect } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import { getResource,postData, validateStringFields, validatePasswordField, validateEmailField, validatePhoneField, validateAdressField } from './../../services/services';

import FormInput from './../form-input/form-input.jsx';
import FormSelect from '../form-select/form-select';
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
    const [address, setAddress] = useState('');
    const [category, setCategory] = useState('');
    const [categories, setCategories] = useState([]);

    const [errorFirstName, setErrorFirstName] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorLastName, setErrorLastName] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorDob, setErrorDob] = useState({errorState: false, messagge: " (Nie określono daty)"});
    const [errorPhone, setErrorPhone] = useState({errorState: false, messagge: " (Musi zaczynać się od +48...)"});
    const [errorEmail, setErrorEmail] = useState({errorState: false, messagge: " (Musi być w postaci *@*.*)"});
    const [errorPassword, setErrorPassword] = useState({errorState: false, messagge: " (Musi zawierać co najmniej 6 znaków)"});
    const [errorCountry, setErrorCountry] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorCity, setErrorCity] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorAddress, setErrorAddress] = useState({errorState: false, messagge: " (Musi byc w postaci ulica, numer)"});


    const eye = <FontAwesomeIcon icon={faEye} />;
    const eyeSlash = <FontAwesomeIcon icon={faEyeSlash} />;

    useEffect(() => {
        console.log("get categories")
        getResource("http://localhost:8080/categories")
            .then(categories => setCategories(categories));
    }, []);

    const validateFields = () => {
        let valid = true;
        if (!validateStringFields(firstName)) {
            setErrorFirstName({...errorFirstName, errorState : true});
            valid = false;
        }
        if (!validateStringFields(lastName)) {
            setErrorLastName({...errorLastName, errorState : true});
            valid = false;
        }
        if (!validateStringFields(country)) {
            setErrorCountry({...errorCountry, errorState : true});
            valid = false;
        }
        if (!validateStringFields(city)) {
            setErrorCity({...errorCity, errorState : true});
            valid = false;
        }
        if (!validatePasswordField(password)) {
            setErrorPassword({...errorPassword, errorState : true});
            valid = false;
        }
        if (!validateEmailField(email)) {
            setErrorEmail({...errorEmail, errorState : true});
            valid = false;
        }
        if (!validatePhoneField(phone)) {
            setErrorPhone({...errorPhone, errorState : true});
            valid = false;
        }
        if (!validateAdressField(address)) {
            setAddress({...errorAddress, errorState : true})
            valid = false;
        }

        return valid;
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

    const renderCategories = () => {
        return categories.map(({id, name}) => {
            return <option key={id} name={name} value={name}>{name}</option>
        })
    }



    const handleSubmit = (event) => {
        event.preventDefault();

        const data = {
            firstName,
            lastName,
            dob,
            phone,
            email,
            password,
            country,
            city,
            address
        }

        if (validateFields()) {
            postData("http://localhost:8080/subscribers", JSON.stringify(data))
            .then(res => {
                console.log(res)
            });
            // .finally(() => {
            //     document.getElementById("create-subscriber-form").reset();
            // });
        }


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
                    handleChange={(e) => handleChange(e, setAddress)}
                    clearError={() => clearErrorAfterFocus(errorAddress ,setErrorAddress)}
                    error={errorAddress}
                    name="street"
                    type="text"
                    label="Adres(Ulica, numer budynku)"
                    value={address}
                    required
                />

                <FormSelect
                    handleChange={(e) => handleChange(e, setCategory)}
                    name={"category"}
                    label="Kategoria"
                    value={category}
                    required>
                    {renderCategories()}
                </FormSelect>

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