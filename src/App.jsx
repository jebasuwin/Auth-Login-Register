import React, { useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import ProductList from './components/ProductList';

const App = () => {
  const [token, setToken] = useState(localStorage.getItem('token'));

  return (
    <div>
      <Routes>
        {/* Redirect root path to login page */}
        <Route path="/" element={<Login setToken={setToken} />} />
        <Route path="/login" element={<Login setToken={setToken} />} />
        <Route path="/register" element={<Register />} />
        <Route path="/product-list" element={<ProductList token={token} setToken={setToken} />} />
      </Routes>
    </div>
  );
};

export default App;
