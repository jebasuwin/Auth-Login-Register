import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ProductList = ({ token, setToken }) => {
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [editedProduct, setEditedProduct] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/products', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProducts(response.data);
      } catch (error) {
        console.log('Error fetching products:', error);
      }
    };
    fetchProducts();
  }, [token]);

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/products/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setProducts(products.filter((product) => product.id !== id));
    } catch (error) {
      console.log('Error deleting product:', error);
    }
  };

  const handleEdit = (product) => {
    setIsEditing(true);
    setEditedProduct(product);
  };

  const handleSave = async () => {
    if (!editedProduct?.name || !editedProduct?.price) {
      console.log("Product name and price are required!");
      return;
    }

    try {
      if (editedProduct?.id) {
        // Edit existing product
        await axios.put(`http://localhost:8080/products/${editedProduct.id}`, editedProduct, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProducts(products.map((product) => (product.id === editedProduct.id ? editedProduct : product)));
      } else {
        // Add new product (POST request)
        const response = await axios.post('http://localhost:8080/products', editedProduct, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setProducts([...products, response.data]);
      }
      setIsEditing(false);
      setEditedProduct(null);
    } catch (error) {
      console.log('Error saving product:', error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null);
    navigate('/login');
  };

  return (
    <div className="container mt-5">
      <h2 className="text-center mb-4">Product List</h2>
      <div className="mb-3">
        <button className="btn btn-success" onClick={() => setIsEditing(true)}>Add Product</button>
        <button className="btn btn-danger ms-3" onClick={handleLogout}>Logout</button>
      </div>
      {isEditing && (
        <div className="card p-4 shadow mb-3">
          <h3>{editedProduct ? 'Edit Product' : 'Add New Product'}</h3>
          <div className="mb-3">
            <label htmlFor="productName" className="form-label">Product Name</label>
            <input
              type="text"
              className="form-control"
              value={editedProduct ? editedProduct.name : ''}
              onChange={(e) => setEditedProduct({ ...editedProduct, name: e.target.value })}
            />
          </div>
          <div className="mb-3">
            <label htmlFor="productPrice" className="form-label">Product Price</label>
            <input
              type="number"
              className="form-control"
              value={editedProduct ? editedProduct.price : ''}
              onChange={(e) => setEditedProduct({ ...editedProduct, price: e.target.value })}
            />
          </div>
          <button className="btn btn-primary" onClick={handleSave}>
            {editedProduct ? 'Save Changes' : 'Add Product'}
          </button>
        </div>
      )}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {products.map((product) => (
            <tr key={product.id}>
              <td>{product.name}</td>
              <td>{product.price}</td>
              <td>
                <button className="btn btn-info" onClick={() => handleEdit(product)}>Edit</button>
                &nbsp;&nbsp;&nbsp;
                <button className="btn btn-danger" onClick={() => handleDelete(product.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProductList;
