import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  Button, Grid, Typography, Box, CircularProgress,
  Paper, Tooltip
} from '@mui/material';
import ManIcon from '@mui/icons-material/Man';
import WomanIcon from '@mui/icons-material/Woman';
import EventSeatIcon from '@mui/icons-material/EventSeat';

const SeatLayout = ({ flight, onSeatSelected, onClose }) => {
  const [seats, setSeats] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedSeat, setSelectedSeat] = useState(null);
  const [error, setError] = useState(null);

  // Fetch seat data when component mounts
  useEffect(() => {
    const fetchSeats = async () => {
      try {
        // Replace with your actual API endpoint
        const response = await axios.get(`http://localhost:8081/seats/flight/${flight.flightId}`);
        // If your API doesn't have this endpoint yet, this is a placeholder for expected data structure
        setSeats(response.data || generateMockSeats(flight.seats));
        setLoading(false);
      } catch (error) {
        console.error("Failed to fetch seats:", error);
        setError("Failed to load seat information");
        // For development, generate mock seats if API fails
        setSeats(generateMockSeats(flight.seats));
        setLoading(false);
      }
    };

    fetchSeats();
  }, [flight.flightId, flight.seats]);

  // Helper function to generate mock seat data during development
  const generateMockSeats = (totalSeats) => {
    const mockSeats = [];
    const rows = Math.ceil(totalSeats / 6); // 6 seats per row (3 on each side)
    
    for (let row = 1; row <= rows; row++) {
      for (let col of ['A', 'B', 'C', 'D', 'E', 'F']) {
        const seatNumber = `${row}${col}`;
        if (mockSeats.length < totalSeats) {
          // Generate some random occupied seats for demonstration
          const isOccupied = Math.random() < 0.3; // 30% chance of being occupied
          const gender = isOccupied ? (Math.random() < 0.5 ? 'MALE' : 'FEMALE') : null;
          
          mockSeats.push({
            id: mockSeats.length + 1,
            seatNumber,
            isOccupied,
            gender,
            flightId: flight.flightId
          });
        }
      }
    }
    return mockSeats;
  };

  const handleSeatClick = (seat) => {
    if (!seat.isOccupied) {
      setSelectedSeat(seat);
    }
  };

  const handleConfirm = () => {
    if (selectedSeat) {
      onSeatSelected(selectedSeat);
    }
  };

  // Organize seats into rows for display
  const seatRows = {};
  seats.forEach(seat => {
    const rowNum = seat.seatNumber.match(/\d+/)[0];
    if (!seatRows[rowNum]) {
      seatRows[rowNum] = [];
    }
    seatRows[rowNum].push(seat);
  });

  // Sort seats in each row by seat letter
  Object.keys(seatRows).forEach(rowNum => {
    seatRows[rowNum].sort((a, b) => a.seatNumber.localeCompare(b.seatNumber));
  });

  // Get sorted row numbers
  const sortedRowNums = Object.keys(seatRows).sort((a, b) => parseInt(a) - parseInt(b));

  // Function to get seat color based on gender
  const getSeatColor = (seat) => {
    if (!seat.isOccupied) return 'primary';
    return seat.gender === 'MALE' ? 'info' : 'secondary';
  };

  // Function to get tooltip text based on seat status
  const getSeatTooltip = (seat) => {
    if (!seat.isOccupied) return `Seat ${seat.seatNumber} - Available`;
    return `Seat ${seat.seatNumber} - Occupied (${seat.gender.charAt(0) + seat.gender.slice(1).toLowerCase()})`;
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="300px">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="300px">
        <Typography color="error">{error}</Typography>
      </Box>
    );
  }

  return (
    <>
      <DialogTitle>
        Select a Seat - {flight.flightName} ({flight.departureTime} - {flight.arrivalTime})
      </DialogTitle>
      <DialogContent>
        <Box mb={2}>
          <Typography variant="subtitle2" gutterBottom>
            Seat Legend:
          </Typography>
          <Grid container spacing={2} alignItems="center">
            <Grid item>
              <EventSeatIcon color="primary" /> Available
            </Grid>
            <Grid item>
              <EventSeatIcon color="info" /> Male Passenger
            </Grid>
            <Grid item>
              <EventSeatIcon color="secondary" /> Female Passenger
            </Grid>
          </Grid>
        </Box>

        <Paper elevation={3} sx={{ p: 3, backgroundColor: '#f5f5f5' }}>
          {sortedRowNums.map(rowNum => (
            <Grid container key={rowNum} spacing={1} mb={1} alignItems="center">
              <Grid item xs={1}>
                <Typography variant="body2">{rowNum}</Typography>
              </Grid>
              <Grid item xs={10}>
                <Grid container spacing={1} justifyContent="center">
                  {seatRows[rowNum].slice(0, 3).map(seat => (
                    <Grid item key={seat.id}>
                      <Tooltip title={getSeatTooltip(seat)}>
                        <Box sx={{ cursor: !seat.isOccupied ? 'pointer' : 'not-allowed' }}>
                          <EventSeatIcon
                            color={getSeatColor(seat)}
                            fontSize="large"
                            sx={{
                              opacity: seat.isOccupied ? 0.7 : 1,
                              transform: selectedSeat?.id === seat.id ? 'scale(1.2)' : 'scale(1)',
                              border: selectedSeat?.id === seat.id ? '2px solid #000' : 'none',
                              borderRadius: '4px',
                            }}
                            onClick={() => handleSeatClick(seat)}
                          />
                          <Typography variant="caption" display="block" textAlign="center">
                            {seat.seatNumber}
                          </Typography>
                        </Box>
                      </Tooltip>
                    </Grid>
                  ))}
                  <Grid item xs={1}>
                    <Box width={20} /> {/* Aisle space */}
                  </Grid>
                  {seatRows[rowNum].slice(3, 6).map(seat => (
                    <Grid item key={seat.id}>
                      <Tooltip title={getSeatTooltip(seat)}>
                        <Box sx={{ cursor: !seat.isOccupied ? 'pointer' : 'not-allowed' }}>
                          <EventSeatIcon
                            color={getSeatColor(seat)}
                            fontSize="large"
                            sx={{
                              opacity: seat.isOccupied ? 0.7 : 1,
                              transform: selectedSeat?.id === seat.id ? 'scale(1.2)' : 'scale(1)',
                              border: selectedSeat?.id === seat.id ? '2px solid #000' : 'none',
                              borderRadius: '4px',
                            }}
                            onClick={() => handleSeatClick(seat)}
                          />
                          <Typography variant="caption" display="block" textAlign="center">
                            {seat.seatNumber}
                          </Typography>
                        </Box>
                      </Tooltip>
                    </Grid>
                  ))}
                </Grid>
              </Grid>
            </Grid>
          ))}
        </Paper>

        {selectedSeat && (
          <Box mt={2}>
            <Typography>
              Selected Seat: <strong>{selectedSeat.seatNumber}</strong>
            </Typography>
          </Box>
        )}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">
          Cancel
        </Button>
        <Button
          onClick={handleConfirm}
          color="primary"
          variant="contained"
          disabled={!selectedSeat}
        >
          Proceed to Booking
        </Button>
      </DialogActions>
    </>
  );
};

export default SeatLayout;
