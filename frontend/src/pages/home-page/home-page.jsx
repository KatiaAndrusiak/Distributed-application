import './home-page.scss'
import { Link } from 'react-router-dom';
import {useSelector} from 'react-redux';

const HomePage = () => {

    const {user, isLogged} = useSelector(state => state)



    const mainContent = isLogged ?  <> <p>Jesteś na stronie serwisu JABEDA. Serwis ten jest stworzony dla rozwiązywania problemów twojego miasta.</p>
                                    <p>Zobacz jakie <Link to="/problems">problemy</Link>  są poblizu {user.city}, {user.street}, {user.buildingNumber}</p> </>
                                :
                                    <> <p>Jesteś na stronie serwisu JABEDA. Serwis ten jest stworzony dla rozwiązywania problemów twojego miasta.</p>
                                    <p>Jeśli chcesz zrobić swoje miasto odrobinę lepszym, to możesz <Link to="/signup">zarejestrować się</Link>  i pomagać mieszkańcom rozwiązywać ich problemy.</p>
                                    <p>Dbajmy razem o dobrobyt naszego miasta!</p> </>


    return (
        <div className="home-page">
            <h1 className="main-title">{isLogged ? `Witaj, ${user.firstName} ${user.lastName}!` : 'Witaj!'}</h1>
            <div className="main-content">
                {mainContent}
            </div>
        </div>
    )  
}

export default HomePage;