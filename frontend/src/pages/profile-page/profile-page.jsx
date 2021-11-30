import './profile-page.scss'
import CustomButton from '../../components/button/custom-button';
import {useSelector} from 'react-redux';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faPhone, faEnvelope, faAddressCard, faTasks } from "@fortawesome/free-solid-svg-icons";
import { Link } from 'react-router-dom';

const Profile = () => {

    const {user: {firstName, lastName, phone, email, categories, city, street, buildingNumber}} = useSelector(state => state);

    const userIcon = <FontAwesomeIcon icon={faUser} />;
    const phomeIcon = <FontAwesomeIcon icon={faPhone} />;
    const mailIcon = <FontAwesomeIcon icon={faEnvelope} />;
    const addressIcon = <FontAwesomeIcon icon={faAddressCard} />;
    const categoryIcon = <FontAwesomeIcon icon={faTasks} />;

    return (
        <div className="profile-page">
            <h1 className="profile-title">Szczegóły użytkownika</h1>
            <ul className="user-information">
                <li className="information-item">
                    <p><strong>Imię {userIcon}</strong></p>
                    <p>{firstName}</p>
                </li>
                <li className="information-item">
                    <p><strong>Nazwisko {userIcon}</strong></p>
                    <p>{lastName}</p>
                </li>
                <li className="information-item">
                    <p><strong>Numer telefonu {phomeIcon}</strong></p>
                    <p>{phone}</p>
                </li>
                <li className="information-item">
                    <p><strong>Email {mailIcon}</strong></p>
                    <p>{email}</p>
                </li>
                <li className="information-item">
                    <p><strong>Adres {addressIcon}</strong></p>
                    <p>{`${city}, ${street}, ${buildingNumber}`}</p>
                </li>
                <li className="information-item">
                    <p><strong>Kategoria (e) {categoryIcon}</strong></p>
                    <p>{categories.join(', ')}</p>
                </li>
            </ul>
            <Link to='/profile/edit'>
                <CustomButton 
                type="button"
                additionalClass="edit">
                    Modyfikuj profil
                </CustomButton>
            </Link>
        </div>
    )
}

export default Profile;