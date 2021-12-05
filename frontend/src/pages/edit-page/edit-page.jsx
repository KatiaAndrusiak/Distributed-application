import './edit-page.scss'
import { useState, useEffect } from 'react';
import {useSelector, useDispatch} from 'react-redux';
import { logout } from '../../redux/user/user-action';


import { getResource, fetchWithAuthorization, validateStringFields, validatePasswordField, validatePhoneField, validateAdressField, closeModal, createModalContent, setModalAndLoading } from './../../services/services';

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import FormInput from '../../components/form-input/form-input';
import FormSelect from '../../components/form-select/form-select';
import CustomButton from '../../components/button/custom-button';
import Spinner from './../../components/spinner/Spinner';
import Modal from './../../components/modal/modal';

const EditPage = () => {
    const {user: {firstName, lastName, phone, email, categories, city, street, buildingNumber, accessToken}} = useSelector(state => state);
    const dispatch = useDispatch();

    const [loading, setLoading] = useState(false);
    const [isModal, setIsModal] = useState(false);
    const [okStatus, setOkStatus] = useState(0);
    const [modalError, setModalError] = useState(false);
    const [modalContent, setModalContent] = useState({});

    const [oldPasswordShown, setOldPasswordShown] = useState(false);
    const [newPasswordShown, setNewPasswordShown] = useState(false);
    const [newPhone, setNewPhone] = useState(phone);
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    // eslint-disable-next-line
    const [country, setCountry] = useState('Polska');
    const [newCity, setNewCity] = useState(city);
    const [newAddress, setNewAddress] = useState(`${street}, ${buildingNumber}`);
    const [newCategory, setNewCategory] = useState([]);
    const [newCategories, setNewCategories] = useState([]);
    

    const [errorPhone, setErrorPhone] = useState({errorState: false, messagge: " (Musi zaczynać się od +48...)"});
    const [errorPassword, setErrorPassword] = useState({errorState: false, messagge: " (Musi zawierać co najmniej 6 znaków)"});
    const [errorNewPassword, setErrorNewPassword] = useState({errorState: false, messagge: " (Musi zawierać co najmniej 6 znaków)"});
    const [errorCity, setErrorCity] = useState({errorState: false, messagge: " (Musi zawierać tylko litery)"});
    const [errorAddress, setErrorAddress] = useState({errorState: false, messagge: " (Wpisz numer domu oddzielony przecinkiem)"});
    const [errorCategory, setErrorCategory] = useState({errorState: false, messagge: "Kategoria (Pole nie moze być puste)"});

    const eye = <FontAwesomeIcon icon={faEye} />;
    const eyeSlash = <FontAwesomeIcon icon={faEyeSlash} />;

    useEffect(() => {
        setNewCategory(() => {
            return categories.map(category => {
                return { value: category, label: category }
            })
        })
        console.log(`${process.env.REACT_APP_API_ROOT_URL}/categories`)
        getResource(`${process.env.REACT_APP_API_ROOT_URL}/categories`)
            .then(categories => setNewCategories(categories));
            // eslint-disable-next-line
    }, []);

    useEffect(() => {
        if (!isModal && !modalError && okStatus === 200) {
            dispatch(logout());
        }
        // eslint-disable-next-line
    }, [isModal, modalError])

    
    const handleChange = (event, setter) => {
        const {value} = event.target;
        setter(value);
    }

    const clearErrorAfterFocus = (value, setter) => {
        setter({...value, errorState: false});
    }

    const togglePasswordVisiblity = () => {
        setOldPasswordShown(oldPasswordShown ? false : true);
    };

    const toggleConfirmPasswordVisiblity = () => {
        setNewPasswordShown(newPasswordShown ? false : true);
    };

    const renderCategories = () => {
        return newCategories.map(({name}) => {
            return { value: name, label: name }
        })
    }

    const handleCategory = (selectedOptions) => {
        console.log(selectedOptions)
        setNewCategory(selectedOptions);
    }

    const validateFields = () => {
        let valid = true;
        if (!validateStringFields(newCity)) {
            setErrorCity({...errorCity, errorState : true});
            valid = false;
        }
        if (!validatePasswordField(oldPassword)) {
            setErrorPassword({...errorPassword, errorState : true});
            valid = false;
        }
        if (!validatePasswordField(newPassword)) {
            setErrorNewPassword({...errorNewPassword, errorState : true});
            valid = false;
        }
        if (!validatePhoneField(newPhone)) {
            setErrorPhone({...errorPhone, errorState : true});
            valid = false;
        }
        if (!validateAdressField(newAddress)) {
            setErrorAddress({...errorAddress, errorState : true});
            valid = false;
        }
        if (newCategory.length === 0) {
            setErrorCategory({...errorCategory, errorState : true});
            valid = false;
        }

        return valid;
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        const transformCategory = newCategory.map(({value}) => value)
        console.log(transformCategory)

        const data = {
            firstName,
            lastName,
            phone: newPhone,
            email,
            oldPassword,
            newPassword,
            country: country,
            city: newCity,
            address: newAddress,
            category: transformCategory,
            role: ["user"]
        }

        console.log(data)

        if (validateFields()) {
            fetchWithAuthorization(`${process.env.REACT_APP_API_ROOT_URL}/auth/edit`, JSON.stringify(data), accessToken, "PUT")
            .then(res => {
                    console.log(res);
                    const {status} = res;
                    if (status === 200) {
                        setOkStatus(200);
                        const messages = [];
                        messages.push("Udało się, załoguj się ponownie, aby korzystać z serwisu"); 
                        setModalContent(createModalContent("Dane zostale zmienione", messages));
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
                    } else if (status === 401) {
                        const messages = [];
                        messages.push("Nieprawidlowe hasło, proszę spróbować jeszcze raz!");
                        setErrorPassword({...errorPassword, errorState : true}); 
                        setModalContent(createModalContent("Błąd", messages));
    
                        setModalAndLoading(true, true, false, setIsModal, setModalError, setLoading);
                    }
                     else if (status === 500) {
                        const messages = [];
                        messages.push("Problem z serwerem, proszę spróbować pózniej"); 
                        setModalContent(createModalContent("Błąd", messages));

                        setModalAndLoading(true, true, false, setIsModal, setModalError, setLoading);
                    }
                    console.log(res)
            })

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
            additionalClass="edit">
                Edytuj
        </CustomButton>

    return (
        <div className="edit-page">
            <form id="edit-subscriber-form" onSubmit={handleSubmit}>
                <FormInput
                    handleChange={(e) => {}}
                    clearError={() => {}}
                    error={null}
                    name="firstName"
                    type="text"
                    label="Imię"
                    value={firstName}
                    required
                    disabled
                />
                <FormInput
                    handleChange={(e) => {}}
                    clearError={() => {}}
                    error={null}
                    name="lastName"
                    type="text"
                    label="Nazwisko"
                    value={lastName}
                    required
                    disabled
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setNewPhone)}
                    clearError={() => clearErrorAfterFocus(errorPhone ,setErrorPhone)}
                    error={errorPhone}
                    name="phone"
                    type="text"
                    label="Numer telefonu (+48)"
                    value={newPhone}
                    required
                />
                <FormInput
                    handleChange={(e) => {}}
                    clearError={() => {}}
                    error={null}
                    name="email"
                    type="text"
                    label="E-mail"
                    value={email}
                    required
                    disabled
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setOldPassword)}
                    clearError={() => clearErrorAfterFocus(errorPassword ,setErrorPassword)}
                    error={errorPassword}
                    name="password"
                    type={oldPasswordShown ? "text" : "password"}
                    label="Stare hasło"
                    value={oldPassword}
                    i={<i onClick={togglePasswordVisiblity}>{oldPasswordShown ? eyeSlash : eye}</i>}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setNewPassword)}
                    clearError={() => clearErrorAfterFocus(errorNewPassword ,setErrorNewPassword)}
                    error={errorNewPassword}
                    name="newPassword"
                    type={newPasswordShown ? "text" : "password"}
                    label="Nowe hasło"
                    value={newPassword}
                    i={<i onClick={toggleConfirmPasswordVisiblity}>{newPasswordShown ? eyeSlash : eye}</i>}
                    required
                />
                <FormInput
                    handleChange={(e) => {}}
                    clearError={() => {}}
                    error={null}
                    name="country"
                    type="text"
                    label="Kraj"
                    value={country}
                    required
                    disabled
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setNewCity)}
                    clearError={() => clearErrorAfterFocus(errorCity ,setErrorCity)}
                    error={errorCity}
                    name="city"
                    type="text"
                    label="Miasto"
                    value={newCity}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setNewAddress)}
                    clearError={() => clearErrorAfterFocus(errorAddress ,setErrorAddress)}
                    error={errorAddress}
                    name="street"
                    type="text"
                    label="Adres(Ulica, numer budynku)"
                    value={newAddress}
                    required
                />
                <FormSelect
                    styles={{
                        control: (provided, state) => ({
                            ...provided,
                            boxShadow: "none",
                            border: state.isFocused && "none",
                        })}}
                    value={newCategory}
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
    )
}

export default EditPage;