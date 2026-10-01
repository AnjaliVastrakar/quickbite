
import { useEffect, useState } from "react";

function Cart({ onBack }) {

  const [cartItems, setCartItems] = useState([]);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const [showConfirmation, setShowConfirmation] = useState(false);

  const userId = 21;

  // =========================
  // LOAD CART
  // =========================

  const loadCart = async () => {

    const token = localStorage.getItem("token");

    try {

      const response = await fetch(
        `http://localhost:8089/api/cart/user/${userId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          }
        }
      );

      if (!response.ok) {
        throw new Error(
          `Failed to load cart: ${response.status}`
        );
      }

      const data = await response.json();

      console.log("Cart items:", data);

      setCartItems(data);

    } catch (error) {

      console.error("Cart Error:", error);

      setError("Unable to load cart");
    }
  };


  useEffect(() => {
    loadCart();
  }, []);


  // =========================
  // UPDATE QUANTITY
  // =========================

  const updateQuantity = async (item, newQuantity) => {

    if (newQuantity < 1) {
      return;
    }

    const token = localStorage.getItem("token");

    setError("");
    setMessage("");

    try {

      const response = await fetch(
        `http://localhost:8089/api/cart/${item.id}?quantity=${newQuantity}`,
        {
          method: "PUT",
          headers: {
            "Authorization": `Bearer ${token}`
          }
        }
      );

      const data = await response.json();

      console.log(
        "Update Quantity Status:",
        response.status
      );

      console.log(
        "Update Quantity Response:",
        data
      );

      if (!response.ok) {

        setError(
          data.message ||
          "Unable to update quantity"
        );

        return;
      }

      loadCart();

    } catch (error) {

      console.error(
        "Quantity Update Error:",
        error
      );

      setError(
        "Unable to update quantity"
      );
    }
  };


  // =========================
  // INCREASE QUANTITY
  // =========================

  const increaseQuantity = (item) => {

    updateQuantity(
      item,
      item.quantity + 1
    );
  };


  // =========================
  // DECREASE QUANTITY
  // =========================

  const decreaseQuantity = (item) => {

    if (item.quantity <= 1) {
      return;
    }

    updateQuantity(
      item,
      item.quantity - 1
    );
  };


  // =========================
  // REMOVE ITEM
  // =========================

  const removeItem = async (id) => {

    const token = localStorage.getItem("token");

    setError("");
    setMessage("");

    try {

      const response = await fetch(
        `http://localhost:8089/api/cart/${id}`,
        {
          method: "DELETE",
          headers: {
            "Authorization": `Bearer ${token}`
          }
        }
      );

      if (!response.ok) {
        throw new Error(
          "Failed to remove item"
        );
      }

      setMessage(
        "Item removed from cart 🗑️"
      );

      loadCart();

    } catch (error) {

      console.error(
        "Remove Cart Error:",
        error
      );

      setError(
        "Unable to remove item"
      );
    }
  };


  // =========================
  // SHOW CHECKOUT
  // =========================

  const openCheckout = () => {

    setError("");
    setMessage("");

    if (cartItems.length === 0) {

      setError(
        "Your cart is empty"
      );

      return;
    }

    setShowConfirmation(true);
  };


  // =========================
  // CANCEL CHECKOUT
  // =========================

  const cancelCheckout = () => {

    setShowConfirmation(false);
  };


  // =========================
  // PLACE ORDER
  // =========================

  const placeOrder = async () => {

    const token = localStorage.getItem("token");

    setError("");
    setMessage("");

    try {

      const response = await fetch(
        "http://localhost:8089/api/orders",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },

          body: JSON.stringify({
            userId: userId
          })
        }
      );

      const data = await response.json();

      console.log(
        "Order API Status:",
        response.status
      );

      console.log(
        "Order response:",
        data
      );

      if (!response.ok) {

        setError(
          data.message ||
          "Failed to place order"
        );

        return;
      }

      setShowConfirmation(false);

      setMessage(
        `Order placed successfully! 🎉 Order ID: ${data.id}`
      );

      loadCart();

    } catch (error) {

      console.error(
        "Order Error:",
        error
      );

      setError(
        "Unable to place order"
      );
    }
  };


  // =========================
  // CALCULATE TOTAL
  // =========================

  const total = cartItems.reduce(
    (sum, item) =>
      sum +
      (
        Number(item.price) *
        Number(item.quantity)
      ),
    0
  );


  // =========================
  // UI
  // =========================

  return (

    <div className="cart-page">

      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back
      </button>


      <h2>
        🛒 My Cart
      </h2>


      {error && (
        <div className="cart-error">
          ❌ {error}
        </div>
      )}


      {message && (
        <div className="cart-success">
          ✅ {message}
        </div>
      )}


      {/* EMPTY CART */}

      {cartItems.length === 0 ? (

        <div className="cart-empty">

          <div className="cart-empty-icon">
            🛒
          </div>

          <h3>
            Your Cart is Empty
          </h3>

          <p>
            Add some delicious food to your cart.
          </p>

        </div>

      ) : (

        <>

          {/* CART ITEMS */}

          <div className="cart-grid">

            {cartItems.map((item) => (

              <div
                className="cart-card"
                key={item.id}
              >

                <div className="cart-card-header">

                  <h3>
                    🍔 {item.menuItemName}
                  </h3>

                </div>


                <div className="cart-item-info">

                  <p>
                    <strong>
                      Price
                    </strong>

                    <span>
                      ₹{item.price}
                    </span>
                  </p>


                  <p>
                    <strong>
                      Quantity
                    </strong>

                    <span className="quantity-controls">

                      <button
                        className="quantity-button"
                        onClick={() =>
                          decreaseQuantity(item)
                        }
                        disabled={item.quantity <= 1}
                      >
                        −
                      </button>


                      <strong className="quantity-number">
                        {item.quantity}
                      </strong>


                      <button
                        className="quantity-button"
                        onClick={() =>
                          increaseQuantity(item)
                        }
                      >
                        +
                      </button>

                    </span>

                  </p>


                  <p className="cart-subtotal">

                    <strong>
                      Subtotal
                    </strong>

                    <span>
                      ₹
                      {
                        Number(item.price) *
                        Number(item.quantity)
                      }
                    </span>

                  </p>

                </div>


                <button
                  className="remove-cart-button"
                  onClick={() =>
                    removeItem(item.id)
                  }
                >
                  🗑️ Remove Item
                </button>

              </div>

            ))}

          </div>


          {/* CART SUMMARY */}

          <div className="cart-summary">

            <h3>
              🧾 Order Summary
            </h3>

            <div className="cart-total-row">

              <span>
                Total Amount
              </span>

              <strong>
                ₹{total}
              </strong>

            </div>


            <button
              className="place-order-button"
              onClick={openCheckout}
            >
              Place Order 🍔
            </button>

          </div>


          {/* CHECKOUT CONFIRMATION */}

          {showConfirmation && (

            <div className="checkout-confirmation">

              <div className="checkout-card">

                <div className="checkout-icon">
                  🧾
                </div>

                <h2>
                  Order Confirmation
                </h2>

                <p className="checkout-subtitle">
                  Please review your order before placing it.
                </p>


                <div className="checkout-items">

                  {cartItems.map((item) => (

                    <div
                      className="checkout-item"
                      key={item.id}
                    >

                      <span>
                        {item.menuItemName}
                        {" × "}
                        {item.quantity}
                      </span>

                      <strong>
                        ₹
                        {
                          Number(item.price) *
                          Number(item.quantity)
                        }
                      </strong>

                    </div>

                  ))}

                </div>


                <div className="checkout-total">

                  <span>
                    Total
                  </span>

                  <strong>
                    ₹{total}
                  </strong>

                </div>


                <p className="checkout-question">
                  Are you sure you want to place this order?
                </p>


                <div className="checkout-actions">

                  <button
                    className="confirm-order-button"
                    onClick={placeOrder}
                  >
                    Confirm Order 🍔
                  </button>


                  <button
                    className="cancel-checkout-button"
                    onClick={cancelCheckout}
                  >
                    Cancel
                  </button>

                </div>

              </div>

            </div>

          )}

        </>

      )}

    </div>
  );
}

export default Cart;
