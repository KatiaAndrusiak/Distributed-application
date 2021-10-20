import './sign-up.scss';
import { useState } from 'react';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";

import FormInput from './../form-input/form-input.jsx';

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

    const eye = <FontAwesomeIcon icon={faEye} />;
    const eyeSlash = <FontAwesomeIcon icon={faEyeSlash} />;

    const togglePasswordVisiblity = () => {
        setPasswordShown(passwordShown ? false : true);
    };

    const handleChange = (event, setter) => {
        const {value} = event.target;
        setter(value);
    }

    return (
        <div className="sign-up-wrapper">
            <form method="post">
                <FormInput
                    handleChange={(e) => handleChange(e, setFirstName)}
                    name="firstName"
                    type="text"
                    label="Imię"
                    value={firstName}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setLastName)}
                    name="lastName"
                    type="text"
                    label="Nazwisko"
                    value={lastName}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setDob)}
                    name="dob"
                    type="text"
                    label="Data narodzenia"
                    value={dob}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setPhone)}
                    name="phone"
                    type="text"
                    label="Numer telefonu"
                    value={phone}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setEmail)}
                    name="email"
                    type="text"
                    label="E-mail"
                    value={email}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setPassword)}
                    name="password"
                    type={passwordShown ? "text" : "password"}
                    label="Hasło"
                    value={password}
                    i={<i onClick={togglePasswordVisiblity}>{passwordShown ? eyeSlash : eye}</i>}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setCountry)}
                    name="country"
                    type="text"
                    label="Państwo"
                    value={country}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setCity)}
                    name="city"
                    type="text"
                    label="Miasto"
                    value={city}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setStreet)}
                    name="street"
                    type="text"
                    label="Ulica"
                    value={street}
                    required
                />
                <FormInput
                    handleChange={(e) => handleChange(e, setBuildingNumber)}
                    name="buildingNumber"
                    type="text"
                    label="Numer budynku"
                    value={buildingNumber}
                    required
                />

                <button type="submit">Stworzyć konto</button>
            </form>
        </div>
    );
}

export default SignUp;

{/* <div className="group">
    <input type="text" name="firstName" onChange={(e) => handleChange(e, setFirstName)}/>
    <label className="form-input-label" htmlFor="firstName">Imię</label>
</div>
<div className="group">
    <input type="text" name="lastName"/>
    <label className="form-input-label" htmlFor="lastName">Nazwisko</label>
</div>
<div className="group">
    <input type="text" name="dob"/>
    <label className="form-input-label" htmlFor="dob">Data narodzenia</label>
</div>
<div className="group">
    <input type="text" name="phone"/>
    <label className="form-input-label" htmlFor="phone">Numer telefonu</label>
</div>
<div className="group">
    <input type="text" name="email"/>
    <label className="form-input-label" htmlFor="email">E-mail</label>
</div>
<div className="group pass-wrapper"> 
    <input  type={passwordShown ? "text" : "password"} name="password"/>
    <label className="form-input-label" htmlFor="password">Hasło</label>
    <i onClick={togglePasswordVisiblity}>{eye}</i>
</div>
<div className="group">
    <input type="text" name="country"/>
    <label className="form-input-label" htmlFor="country">Państwo</label>
</div>
<div className="group">
    <input type="text" name="city"/> 
    <label className="form-input-label" htmlFor="city">Miasto</label>
</div>
<div className="group">
    <input type="text" name="street"/>
    <label className="form-input-label" htmlFor="street">Ulica</label>
</div>
<div className="group">    
    <input type="text" name="buildingNumber"/>
    <label className="form-input-label" htmlFor="buildingNumber">Numer budynku</label>
</div> */}