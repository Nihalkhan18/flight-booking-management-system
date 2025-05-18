import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Button from '@mui/material/Button';
import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from '@mui/material/styles';
import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper, CircularProgress,
    Box, Stack, Typography, Dialog, Chip
} from '@mui/material';

import { tableCellClasses } from '@mui/material/TableCell';
import CurrencyRupeeIcon from '@mui/icons-material/CurrencyRupee';
import FlightTakeoffIcon from '@mui/icons-material/FlightTakeoff';
import MaleIcon from '@mui/icons-material/Male';
import FemaleIcon from '@mui/icons-material/Female';
import { blue, pink } from '@mui/material/colors';
import SeatLayout from './SeatLayout'; // Import the SeatLayout component

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: blue[800],
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));

const Flights = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [flights, setFlights] = useState([]);
    const [seatDialogOpen, setSeatDialogOpen] = useState(false);
    const [selectedFlight, setSelectedFlight] = useState(null);

    const { origin, destination, travelDate } = location.state;

    const url = "http://localhost:8084/booking/getByFromToDate";

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(url, {
                    params: {
                        origin,
                        destination,
                        travelDate, // Expects format 'YYYY-MM-DD'
                    }
                });
                
                // The API now returns availableMaleSeats and availableFemaleSeats directly
                // so we don't need to modify the data
                setFlights(response.data);
                console.log(response.data)
                setIsLoaded(true);
            } catch (error) {
                setError(error);
                setIsLoaded(true);
            }
        };
        fetchData();
    }, [origin, destination, travelDate]);

    const formatFare = (fare) => {
        return fare.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    };

    // Handle the "Book" button click
    const handleBookClick = (flight) => {
        setSelectedFlight(flight);
        setSeatDialogOpen(true);
    };

    // Handle seat selection confirmation
    const handleSeatSelected = (seat) => {
        setSeatDialogOpen(false);
        // Navigate to booking form with the flight and selected seat information
        navigate('/book', { 
            state: { 
                flight: selectedFlight,
                selectedSeat: seat 
            } 
        });
    };

    // Close the seat selection dialog
    const handleSeatDialogClose = () => {
        setSeatDialogOpen(false);
    };

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return (
            <Box component={Paper} elevation={5} sx={{ backgroundColor: 'white', borderRadius: 2 }}>
                <Stack sx={{ m: 2, width: 850, display: 'flex', alignItems: 'center' }}>
                    <CircularProgress />
                    <Typography sx={{ mt: 2 }}>Loading flights...</Typography>
                </Stack>
            </Box>
        );
    } else if (flights.length === 0) {
        return (
            <Box component={Paper} elevation={5} sx={{ backgroundColor: 'white', borderRadius: 2 }}>
                <Stack sx={{ m: 2, width: 850, display: 'flex' }}>
                    <FlightTakeoffIcon sx={{ mb: 1, fontSize: '150%', color: 'black' }} />
                    <Typography variant='caption' color='black'>
                        Departing flight
                    </Typography>
                    <Typography variant='h5' color='black'>
                        {origin} to {destination} on {travelDate}
                    </Typography>
                    <Typography sx={{ mt: 5, mb: 2 }} variant='body1' color='black' align='center'>
                        No flights found on {travelDate} from {origin} to {destination}.
                    </Typography>
                </Stack>
            </Box>
        );
    } else {
        return (
            <>
                <Box component={Paper} elevation={5} sx={{ backgroundColor: 'white', borderRadius: 2 }}>
                    <Stack sx={{ m: 2 }}>
                        <FlightTakeoffIcon sx={{ mb: 1, fontSize: '150%', color: 'black' }} />
                        <Typography variant='caption' color='black'>
                            Departing flight
                        </Typography>
                        <Typography variant='h5' color='black'>
                            {origin} to {destination} on {travelDate}
                        </Typography>
                        <TableContainer sx={{ maxHeight: 400, borderRadius: 2 }}>
                            <Table sx={{ minWidth: 800 }} aria-label="flights table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell>Airline</StyledTableCell>
                                        <StyledTableCell>Departure Time</StyledTableCell>
                                        <StyledTableCell>Arrival Time</StyledTableCell>
                                        <StyledTableCell align="center">Available Seats</StyledTableCell>
                                        <StyledTableCell align="right">Fare</StyledTableCell>
                                        <StyledTableCell align="center">Book</StyledTableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {flights.map((flight) => (
                                        <StyledTableRow key={flight.flightId}>
                                            <StyledTableCell>{flight.flightName}</StyledTableCell>
                                            <StyledTableCell>{flight.departureTime}</StyledTableCell>
                                            <StyledTableCell>{flight.arrivalTime}</StyledTableCell>
                                            <StyledTableCell>
                                                <Stack direction="row" spacing={2} justifyContent="center">
                                                    <Chip 
                                                        icon={<MaleIcon />} 
                                                        label={`${flight.availableMaleSeats}`} 
                                                        color="primary"
                                                        size="small"
                                                    />
                                                    <Chip 
                                                        icon={<FemaleIcon />} 
                                                        label={`${flight.availableFemaleSeats}`}
                                                        sx={{ backgroundColor: pink[500], color: 'white' }}
                                                        size="small"
                                                    />
                                                </Stack>
                                            </StyledTableCell>
                                            <StyledTableCell align="right">
                                                <CurrencyRupeeIcon fontSize='inherit' />{formatFare(flight.fare)}
                                            </StyledTableCell>
                                            <StyledTableCell align="center">
                                                <Button 
                                                    variant='contained' 
                                                    onClick={() => handleBookClick(flight)}
                                                >
                                                    Book
                                                </Button>
                                            </StyledTableCell>
                                        </StyledTableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Stack>
                </Box>

                {/* Seat Selection Dialog */}
                <Dialog
                    open={seatDialogOpen}
                    onClose={handleSeatDialogClose}
                    fullWidth
                    maxWidth="md"
                >
                    {selectedFlight && (
                        <SeatLayout
                            flight={selectedFlight}
                            onSeatSelected={handleSeatSelected}
                            onClose={handleSeatDialogClose}
                        />
                    )}
                </Dialog>
            </>
        );
    }
};

export default Flights;