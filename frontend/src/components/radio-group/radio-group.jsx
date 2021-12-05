import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

const RadioButtons = (props) => {
    return (
        <FormControl component="fieldset">
        <FormLabel component="legend">{props.legend}</FormLabel>
        <RadioGroup 
            row aria-label={props.ariaLabel} 
            name="row-radio-buttons-group"
            value={props.value}
            onChange={props.handle}>
            {props.radioButtons.buttons.map(({value, label}, idx) => {
                return <FormControlLabel key={idx} value={value} control={<Radio />} label={label}/>
            })}
        </RadioGroup>
    </FormControl>
    )
}

export default RadioButtons;