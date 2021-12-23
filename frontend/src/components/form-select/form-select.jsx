import './form-select.scss';
import { useEffect } from 'react';
import Select from 'react-select'
import makeAnimated from 'react-select/animated';

const FormSelect = (props) => {
    const {options, placeholder, clearError, error, ...otherProps} = props;

    useEffect(() => {
        const container = document.querySelector(".react-select__control");
        const selectPlaceholder = document.querySelector(".react-select__placeholder ");
        if (error.errorState) {
            container.classList.add("error");
            selectPlaceholder.classList.add("error");
            selectPlaceholder.innerHTML = error.messagge; 
        } else {
            container.classList.remove("error");
            if (selectPlaceholder !== null) {
                selectPlaceholder.classList.remove("error");
                selectPlaceholder.innerHTML = placeholder;
            }

        }
    }, [error])
    
   
    return (
        <div className="select-wrapper">
            <Select
                onFocus={clearError} 
                placeholder
                className='react-select-container'
                classNamePrefix="react-select"
                options={options}
                components={makeAnimated()}
                {...otherProps}
            />
        </div>
    )
}

export default FormSelect;