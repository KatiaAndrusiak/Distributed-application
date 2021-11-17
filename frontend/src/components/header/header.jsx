import './header.scss';
import {useSelector, useDispatch} from 'react-redux';
import { logout } from '../../redux/user/user-action';
import { Link } from 'react-router-dom';

const Header = () => {
    const {isLogged} = useSelector(state => state);
    const dispatch = useDispatch();
    
    

    return (
        <div className="header">
            <Link to="/">
                <img className='logo' src={process.env.PUBLIC_URL + 'logo.png'} alt="logo"/> 
            </Link>
            <div className='options'>
                <div className="option">
                    {
                        isLogged  ?  <Link  to="/problems">
                                        Problemy 
                                    </Link>
                                  : <Link to="/signup">
                                        Rejestracja 
                                    </Link>
                    }
                </div>
                <div className="option">
                    {
                        isLogged  ?  <div className="logout option" onClick={() => {
                            localStorage.removeItem("user");
                            dispatch(logout());

                        }}>
                            Wyloguj się
                        </div>
                        : <Link  to="/signin">
                            Logowanie
                        </Link>
                    }
                </div>
            </div>
        </div>

    )
}

export default Header;