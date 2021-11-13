import './header.scss';

const Header = () => {
    return (
        <div className="header">
            <a href="#">
                <img className='logo' src={process.env.PUBLIC_URL + 'logo.png'} alt="logo"/> 
            </a>
            <div className='options'>
                <div className="option">
                    <a  href="#">
                        Rejestracja 
                    </a>
                </div>
                <div className="option">
                    <a  href="#">
                    Logowanie
                    </a>
                </div>
            </div>
        </div>

    )
}

export default Header;