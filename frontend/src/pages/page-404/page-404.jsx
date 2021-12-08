import './page-404.scss'

import ErrorMessage from "../../components/error-message/error-message";
import {Link} from 'react-router-dom';

const Page404 = () => {
    return (
        <div className='error-page'>
            <ErrorMessage/>
            <p className='error-message'>Strona nie istnieje</p>
            <Link className='home-page-link' to="/">Wróć do strony głównej</Link>
        </div>
    )
}

export default Page404;