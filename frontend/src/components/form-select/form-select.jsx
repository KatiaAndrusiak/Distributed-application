import './form-select.scss';

const FormSelect = (props) => {
    const {label, value, handleChange, ...otherProps} = props;
    const labelClasses = `${value.length ? 'shrink' : ''} form-select-label`;

    return (
        <div className="select-wrapper">
            <select onChange={handleChange} {...otherProps}>
                <option style={{"display":"none"}}></option>
                {props.children}
            </select>
            <label className={labelClasses}>{label}</label>
    </div>
    )
}

export default FormSelect;