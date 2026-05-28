# Retape.al Smart Booking Platform - API Documentation

## Base URL
All requests go through the API Gateway: `http://localhost:8080`

---

## Customer Service (port 8081)

### POST /customers/register
Register a new customer.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@retape.al",
  "password": "1234",
  "phone": "0691234567"
}
```

**Response 200:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@retape.al",
  "phone": "0691234567"
}
```

### POST /customers/login
Login with email and password.

**Params:** `email`, `password`

**Response 200:** Customer object
**Response 401:** Unauthorized

### GET /customers
Get all customers.

**Response 200:** Array of customer objects

### GET /customers/{id}
Get customer by ID.

**Response 200:** Customer object
**Response 404:** Not found

---

## Resource Service (port 8082)

### POST /resources
Create a new digitization resource/package.

**Request Body:**
```json
{
  "name": "USB Copy",
  "description": "Digitize your tape to USB",
  "price": 15.99,
  "availableSlots": 10
}
```

### GET /resources
Get all resources.

### GET /resources/available
Get only resources with available slots.

### GET /resources/{id}
Get resource by ID.

### PUT /resources/{id}/slots?slots=5
Update available slots for a resource.

---

## Booking Service (port 8083)

### POST /bookings
Create a new booking. Triggers a RabbitMQ event to Notification Service.

**Request Body:**
```json
{
  "customerId": 1,
  "resourceId": 1,
  "notes": "VHS tape from 1995"
}
```

**Response 200:**
```json
{
  "id": 1,
  "customerId": 1,
  "resourceId": 1,
  "status": "CONFIRMED",
  "bookingDate": "2026-05-28T08:54:23",
  "notes": "VHS tape from 1995"
}
```

### PUT /bookings/{id}/cancel
Cancel a booking.

**Response 200:** Updated booking with status CANCELLED

### GET /bookings
Get all bookings.

### GET /bookings/{id}
Get booking by ID.

### GET /bookings/customer/{customerId}
Get all bookings for a specific customer.

---

## Payment Service (port 8084)

### POST /payments
Process a payment (simulated).

**Request Body:**
```json
{
  "bookingId": 1,
  "customerId": 1,
  "amount": 15.99
}
```

**Response 200:**
```json
{
  "id": 1,
  "bookingId": 1,
  "customerId": 1,
  "amount": 15.99,
  "status": "SUCCESS",
  "paymentDate": "2026-05-28T08:54:32"
}
```

### GET /payments
Get all payments.

### GET /payments/booking/{bookingId}
Get payments for a specific booking.

### GET /payments/customer/{customerId}
Get payments for a specific customer.

---

## Communication Model

### Synchronous (REST)
- Client ? API Gateway ? Services
- All CRUD operations use REST over HTTP

### Asynchronous (RabbitMQ)
- Booking Service publishes `BOOKING_CREATED` event
- Exchange: `booking.exchange`
- Queue: `booking.queue`
- Notification Service listens and sends confirmation
