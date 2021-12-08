import './sign-up.scss';
import { useState, useEffect } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import { getResource,postData, validateStringFields, validatePasswordField, validateEmailField, validatePhoneField, validateAdressField, closeModal, createModalContent, setModalAndLoading } from './../../services/services';

import FormInput from './../form-input/form-input.jsx';
import FormSelect from '../form-select/form-select';
import CustomButton from './../button/custom-button';
import Spinner from '../spinner/Spinner';
import Modal from '../modal/modal';

const SignUp = () => {

    const [passwordShown, setPasswordShown] = useState(false);
    const [confirmPasswordShown, setConfirmPasswordShown] = useState(false);
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phone, setPhone] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [country, setCountry] = useState('Polska');
    const [city, setCity] = useState('');
    const [address, setAddress] = useState('');
    const [category, setCategory] = useState([]);
    const [categories, setCategories] = useState([]);

    const [loading, setLoading] = useState(false);
    const [isModal, setIsModal] = useState(false);
    const [modalError, setModalError] = useState(false);
    const [modalContent, setModalContent] = useState({});

    const [errorFirstName, setErrorFirstName] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorLastName, setErrorLastName] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorPhone, setErrorPhone] = useState({errorState: false, messagge: " (Musi zaczynać się od +48...)"});
    const [errorEmail, setErrorEmail] = useState({errorState: false, messagge: " (Musi być w postaci *@*.*)"});
    const [errorPassword, setErrorPassword] = useState({errorState: false, messagge: " (Musi zawierać co najmniej 6 znaków)"});
    const [errorConfirmPassword, setErrorConfirmPassword] = useState({errorState: false, messagge: " (Hasło rózni się)"});
    const [errorCountry, setErrorCountry] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorCity, setErrorCity] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorAddress, setErrorAddress] = useState({errorState: false, messagge: " (Wpisz numer domu oddzielony przecinkiem)"});
    const [errorCategory, setErrorCategory] = useState({errorState: false, messagge: "Kategoria (Pole nie moze być puste)"});


    const eye = <FontAwesomeIcon icon={faEye} />;
    const eyeSlash = <FontAwesomeIcon icon={faEyeSlash} />;

    useEffect(() => {
        getResource(`${process.env.REACT_APP_API_ROOT_URL}/categories`)
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
            setErrorAddress({...errorAddress, errorState : true});
            valid = false;
        }
        if (password !== confirmPassword) {
            setErrorConfirmPassword({...errorConfirmPassword, errorState : true});
            valid = false;
        }
        if (category.length === 0) {
            setErrorCategory({...errorCategory, errorState : true});
            valid = false;
        }

        return valid;
    }

    const clearForm = () => {
        setFirstName('');
        setLastName('');
        setPhone('');
        setEmail('');
        setPassword('');
        setConfirmPassword('');
        setCity('');
        setAddress('');
        setCategory([]);
    }

    const togglePasswordVisiblity = () => {
        setPasswordShown(passwordShown ? false : true);
    };

    const toggleConfirmPasswordVisiblity = () => {
        setConfirmPasswordShown(confirmPasswordShown ? false : true);
    };

    const handleChange = (event, setter) => {
        const {value} = event.target;
        setter(value);
    }

    const clearErrorAfterFocus = (value, setter) => {
        setter({...value, errorState: false});
    }

    const renderCategories = () => {
        return categories.map(({name}) => {
            return { value: name, label: name }
        })
    }

    const handleCategory = (selectedOptions) => {
        setCategory(selectedOptions);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        setLoading(true);

        const transformCategory = category.map(({value}) => value)

        const data = {
            firstName,
            lastName,
            phone,
            email,
            password,
            country,
            city,
            address,
            category: transformCategory,
            role: ["user"]
        }

        if (validateFields()) {
            postData(`${process.env.REACT_APP_API_ROOT_URL}/auth/signup`, JSON.stringify(data))
            .then(res => {
                const {status} = res;
                if (status === 200) {
                    const messages = [];
                    messages.push("Udało się, teraz możesz zalogować się i korzystać z serwisu."); 
                    setModalContent(createModalContent("Użytkownik został zarejestrowany", messages));

                    clearForm();
                    document.getElementById("create-subscriber-form").reset();
                    setModalAndLoading(true, false, false, setIsModal, setModalError, setLoading);
                } else if (status === 400 || status === 404) {
                    const messages = []; 
                    for (const key in res) {
                        if (key !== 'status') {
                            messages.push(res[key]);
                        }
                    }
                    setModalContent(createModalContent("Błąd", messages));

                    setModalAndLoading(true, true, false, setIsModal, setModalError, setLoading);
                } else if (status === 500) {
                    const messages = [];
                    messages.push("Problem z serwerem, proszę spróbować pózniej"); 
                    setModalContent(createModalContent("Błąd", messages));

                    setModalAndLoading(true, true, false, setIsModal, setModalError, setLoading);
                }
            })
            .catch(e => console.log(e));
        } else {
            setLoading(false);
        }
    }

    const modal = isModal ? <Modal 
        modalContent = {modalContent}
        modalError={modalError}
        close={() => closeModal(setIsModal)}/> : null
    
    const lastElement = loading ?                 
        <div className="spinner-wrapper">
            <Spinner/>
        </div> :
        <CustomButton 
            type="submit"
            additionalClass="submit">
                Stworzyć konto
        </CustomButton>

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
                    handleChange={(e) => handleChange(e, setPhone)}
                    clearError={() => clearErrorAfterFocus(errorPhone ,setErrorPhone)}
                    error={errorPhone}
                    name="phone"
                    type="text"
                    label="Numer telefonu (+48)"
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
                    handleChange={(e) => handleChange(e, setConfirmPassword)}
                    clearError={() => clearErrorAfterFocus(errorConfirmPassword ,setErrorConfirmPassword)}
                    error={errorConfirmPassword}
                    name="confirmPassword"
                    type={confirmPasswordShown ? "text" : "password"}
                    label="Podaj ponownie hasło"
                    value={confirmPassword}
                    i={<i onClick={toggleConfirmPasswordVisiblity}>{confirmPasswordShown ? eyeSlash : eye}</i>}
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
                    disabled
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
                    styles={{
                        control: (provided, state) => ({
                            ...provided,
                            boxShadow: "none",
                            border: state.isFocused && "none",
                        })}}
                    value={category}
                    onChange={handleCategory}
                    clearError={() => clearErrorAfterFocus(errorCategory ,setErrorCategory)}
                    error={errorCategory}
                    isMulti
                    placeholder={"Kategoria"}
                    clearIndicator={false}
                    dropdownIndicator={false}
                    options={renderCategories()}/>
                    
                {lastElement}
            </form>
            {modal}
        </div>
    );
}

export default SignUp;