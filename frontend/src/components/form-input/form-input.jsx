import './form-input.scss'

const FormInput = ({handleChange, label, i, value, error, clearError, ...otherProps}) => {

    const inputClasses =  error.errorState  ? 'error' : '';
    const labelClasses = `${value.length ? 'shrink' : ''} ${error.errorState  ? 'error' : ''} form-input-label`;
    const lableText = error.errorState ? label + error.messagge : label;

    return (
        <div className="group">
            <input
                onFocus={clearError} 
                className={inputClasses}
                onChange={handleChange} 
                {...otherProps}/>
            <label className={labelClasses}>{lableText}</label>
            {i ? i : null}
        </div>
    )
}

export default FormInput;