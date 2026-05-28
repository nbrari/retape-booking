const express = require('express');
const httpProxy = require('http-proxy');
const cors = require('cors');

const app = express();
app.use(cors());

const proxy = httpProxy.createProxyServer({});

proxy.on('error', (err, req, res) => {
    console.log('Proxy error:', err.message);
    res.writeHead(500);
    res.end('Service unavailable');
});

app.use((req, res) => {
    console.log('Incoming:', req.method, req.url);
    
    if (req.url.startsWith('/customers')) {
        proxy.web(req, res, { target: 'http://localhost:8081' });
    } else if (req.url.startsWith('/resources')) {
        proxy.web(req, res, { target: 'http://localhost:8082' });
    } else if (req.url.startsWith('/bookings')) {
        proxy.web(req, res, { target: 'http://localhost:8083' });
    } else if (req.url.startsWith('/payments')) {
        proxy.web(req, res, { target: 'http://localhost:8084' });
    } else if (req.url === '/') {
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ message: 'Retape.al API Gateway' }));
    } else {
        res.writeHead(404);
        res.end('Not found');
    }
});

app.listen(8080, () => console.log('API Gateway running on port 8080'));
