import './error-message.scss';

const ErrorMessage = () => {
    return (
        <img 
            className="error-img"
            alt="error" 
            src={process.env.PUBLIC_URL + '/error.gif'}
            />
    )
}

export default ErrorMessage;