// src/main/webapp/js/script.js
document.addEventListener('DOMContentLoaded', function() {
    // The base URL should be adjusted to match your deployment context path
    // For a simple deployment on Tomcat, it's often /project-name/
    const contextPath = '/inventory-system'; // <-- ADJUST THIS if your deployment name is different
    const apiUrl = `${contextPath}/api/products`;
    
    const productForm = document.getElementById('product-form');
    const productList = document.getElementById('product-list');
    const productIdInput = document.getElementById('product-id');
    const clearBtn = document.getElementById('clear-btn');

    async function getProducts() {
        const response = await fetch(apiUrl);
        const products = await response.json();
        productList.innerHTML = '';
        products.forEach(product => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${product.name}</td>
                <td>${product.quantity}</td>
                <td>$${product.price.toFixed(2)}</td>
                <td>
                    <button class="btn btn-edit" onclick="editProduct(${product.id}, '${product.name}', ${product.quantity}, ${product.price})">Edit</button>
                    <button class="btn btn-delete" onclick="deleteProduct(${product.id})">Delete</button>
                </td>
            `;
            productList.appendChild(tr);
        });
    }

    productForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const id = productIdInput.value;
        const name = document.getElementById('name').value;
        const quantity = document.getElementById('quantity').value;
        const price = document.getElementById('price').value;

        const product = { name, quantity, price };
        let response;
        if (id) {
            response = await fetch(`${apiUrl}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(product)
            });
        } else {
            response = await fetch(apiUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(product)
            });
        }
        
        if (response.ok) {
            clearForm();
            getProducts();
        } else {
            alert('An error occurred. Please check the data and try again.');
        }
    });

    function clearForm() {
        productIdInput.value = '';
        productForm.reset();
    }
    
    clearBtn.addEventListener('click', clearForm);

    window.editProduct = function(id, name, quantity, price) {
        productIdInput.value = id;
        document.getElementById('name').value = name;
        document.getElementById('quantity').value = quantity;
        document.getElementById('price').value = price;
    }

    window.deleteProduct = async function(id) {
        if (confirm('Are you sure you want to delete this product?')) {
            const response = await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
            if(response.ok) {
                getProducts();
            } else {
                alert('Failed to delete product.');
            }
        }
    }

    getProducts();
});