const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const cors = require('cors');

const app = express();
app.use(cors());

// Routes
app.use('/customers', createProxyMiddleware({ target: 'http://localhost:8081', changeOrigin: true }));
app.use('/resources', createProxyMiddleware({ target: 'http://localhost:8082', changeOrigin: true }));
app.use('/bookings', createProxyMiddleware({ target: 'http://localhost:8083', changeOrigin: true }));
app.use('/payments', createProxyMiddleware({ target: 'http://localhost:8084', changeOrigin: true }));

app.get('/', (req, res) => {
    res.json({
        message: 'Retape.al API Gateway',
        services: {
            customers: 'http://localhost:8080/customers',
            resources: 'http://localhost:8080/resources',
            bookings: 'http://localhost:8080/bookings',
            payments: 'http://localhost:8080/payments'
        }
    });
});

const PORT = 8080;
app.listen(PORT, () => {
    console.log('API Gateway running on port ' + PORT);
});
