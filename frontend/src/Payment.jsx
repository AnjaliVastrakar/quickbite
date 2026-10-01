
import { useState } from "react";

function Payment({ order, onBack }) {

  const [paymentMethod, setPaymentMethod] = useState("UPI");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [paymentSuccess, setPaymentSuccess] = useState(false);
  const [paymentId, setPaymentId] = useState(null);

  // =========================
  // MAKE PAYMENT
  // =========================

  const makePayment = async () => {

    const token = localStorage.getItem("token");

    setMessage("");
    setError("");

    try {

      const response = await fetch(
        "http://localhost:8089/api/payments",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },

          body: JSON.stringify({
            orderId: order.id,
            paymentMethod: paymentMethod
          })
        }
      );

      const data = await response.json();

      console.log(
        "Payment API Status:",
        response.status
      );

      console.log(
        "Payment Response:",
        data
      );

      if (!response.ok) {

        setError(
          data.message ||
          "Payment failed"
        );

        return;
      }

      setPaymentId(data.id);

      setPaymentSuccess(true);

      setMessage(
        "Payment successful! 🎉"
      );

    } catch (error) {

      console.error(
        "Payment Error:",
        error
      );

      setError(
        "Unable to connect to payment service"
      );

    }
  };


  // =========================
  // PAYMENT SUCCESS SCREEN
  // =========================

  if (paymentSuccess) {

    return (

      <div className="payment-page">

        <button
          className="back-button"
          onClick={onBack}
        >
          ← Back to Orders
        </button>


        <h2>
          🎉 Payment Successful
        </h2>


        <div className="payment-card success-card">

          <div className="success-icon">
            ✅
          </div>

          <h3>
            Payment Completed!
          </h3>

          <p className="success-message">
            Your payment has been completed successfully.
          </p>


          <hr />


          <div className="payment-info">

            <p>
              <strong>Order ID:</strong>
              <span>#{order.id}</span>
            </p>

            <p>
              <strong>Payment ID:</strong>
              <span>#{paymentId}</span>
            </p>

            <p>
              <strong>Amount Paid:</strong>
              <span>₹{order.totalAmount}</span>
            </p>

            <p>
              <strong>Payment Method:</strong>
              <span>{paymentMethod}</span>
            </p>

          </div>


          <p className="order-success-message">
            ✅ Your order has been placed successfully.
          </p>


          <button
            className="payment-button"
            onClick={onBack}
          >
            Go to Orders 📦
          </button>

        </div>

      </div>

    );
  }


  // =========================
  // PAYMENT PAGE
  // =========================

  return (

    <div className="payment-page">

      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back
      </button>


      <h2>
        💳 Make Payment
      </h2>


      <div className="payment-card">

        <h3>
          📦 Order #{order.id}
        </h3>


        <div className="payment-order-info">

          <p>
            <strong>Amount:</strong>
            <span>₹{order.totalAmount}</span>
          </p>

          <p>
            <strong>Order Status:</strong>
            <span className="order-status">
              {order.status}
            </span>
          </p>

        </div>


        <hr />


        <div className="payment-method">

          <label>
            <strong>
              💳 Payment Method
            </strong>
          </label>


          <select
            value={paymentMethod}
            onChange={(e) =>
              setPaymentMethod(e.target.value)
            }
          >
            <option value="UPI">
              UPI
            </option>

            <option value="CARD">
              Card
            </option>

            <option value="CASH">
              Cash
            </option>

          </select>

        </div>


        <button
          className="payment-button"
          onClick={makePayment}
        >
          Pay ₹{order.totalAmount} 💳
        </button>


        {message && (
          <p className="payment-success-message">
            ✅ {message}
          </p>
        )}


        {error && (
          <p className="payment-error-message">
            ❌ {error}
          </p>
        )}

      </div>

    </div>

  );
}

export default Payment;