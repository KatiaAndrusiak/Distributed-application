import './header.scss';
import {useSelector, useDispatch} from 'react-redux';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faUserPlus, faSignInAlt, faSignOutAlt, faTasks } from "@fortawesome/free-solid-svg-icons";
import { logout } from '../../redux/user/user-action';
import { Link } from 'react-router-dom';

const Header = () => {
    const {isLogged} = useSelector(state => state);
    const dispatch = useDispatch();

    const userIcon = <FontAwesomeIcon icon={faUser} />;
    const signUpIcon = <FontAwesomeIcon icon={faUserPlus} />;
    const signInIcon = <FontAwesomeIcon icon={faSignInAlt} />;
    const logOutIcon = <FontAwesomeIcon icon={faSignOutAlt} />;
    const problemIcon = <FontAwesomeIcon icon={faTasks} />;
    
    

    return (
        <div className="header">
            <Link to="/">
                <img className='logo' src={process.env.PUBLIC_URL + 'logo.png'} alt="logo"/> 
            </Link>
            <div className='options'>
                <div className="option">
                    {
                        isLogged  ?  <Link  to="/profile">
                                        Profil {userIcon}
                                    </Link>
                                  : null
                    }
                </div>
                <div className="option">
                    {
                        isLogged  ?  <Link  to="/problems">
                                        Problemy {problemIcon}
                                    </Link>
                                  : <Link to="/signup">
                                        Rejestracja {signUpIcon}
                                    </Link>
                    }
                </div>
                <div className="option">
                    {
                        isLogged  ?  <div className="logout" onClick={() => {
                            localStorage.removeItem("user");
                            localStorage.removeItem("acceptedRows");
                            dispatch(logout());

                        }}>
                            Wyloguj się {logOutIcon}
                        </div>
                        : <Link  to="/signin">
                            Logowanie {signInIcon}
                        </Link>
                    }
                </div>
            </div>
        </div>

    )
}

export default Header;