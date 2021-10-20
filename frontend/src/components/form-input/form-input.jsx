import './form-input.scss'

const FormInput = (props) => {

    const {handleChange, label, type, name, required, i, value} = props;

    return (
        <div className="group">
            <input type={type} name={name} onChange={handleChange} required={required}/>
            <label className={`${value.length ? 'shrink' : ''} form-input-label`}>{label}</label>
            {i ? i : null}
        </div>
    )
}

export default FormInput;