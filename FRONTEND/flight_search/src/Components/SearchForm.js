import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import { Box } from '@mui/material';
import Paper from '@mui/material/Paper';

function Form() {
    const navigate = useNavigate();
    const [inputs, setInputs] = useState({});
    const [error, setError] = useState("");

    const handleChange = event => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({ ...values, [name]: value }));
    }

    const handleSubmit = event => {
        event.preventDefault();
        
        // Basic validation for empty inputs
        if (!inputs.origin || !inputs.destination || !inputs.travelDate) {
            setError("All fields are required.");
            return;
        }

        // Reset error message on successful validation
        setError("");

        // Navigate to flight results page with input data
        navigate('/flights', { state: { origin: inputs.origin, destination: inputs.destination, travelDate: inputs.travelDate } });
    }

    return (
        <Box component={Paper} elevation={5} sx={{ backgroundColor: 'white', borderRadius: 2 }}>
            <form onSubmit={handleSubmit}>
                <Stack sx={{ m: 2 }} direction='column' spacing={2}>
                    <TextField
                        required
                        autoFocus
                        id="outlined-required"
                        label="From"
                        name='origin'
                        value={inputs.origin || ""}
                        inputProps={{ pattern: '[a-zA-Z\\s]{3,30}$' }}
                        onChange={handleChange}
                    />
                    <TextField
                        required
                        id="outlined-required"
                        label="To"
                        name='destination'
                        value={inputs.destination || ""}
                        inputProps={{ pattern: '[a-zA-Z\\s]{3,30}$' }}
                        onChange={handleChange}
                    />
                    <TextField
                        required
                        type="date"
                        id="outlined-required"
                        label="Travel Date"
                        name="travelDate"
                        value={inputs.travelDate || ""}
                        onChange={handleChange}
                        InputLabelProps={{ shrink: true }}
                    />
                    <Button variant='contained' type='submit'>Search Flight</Button>
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                </Stack>
            </form>
        </Box>
    );
}

export default Form;
