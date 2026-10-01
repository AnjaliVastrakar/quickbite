
import { useEffect, useState } from "react";

function Orders({ onBack, onViewDetails, onPay }) {

  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const userId = 21;


  // =========================
  // LOAD ORDERS
  // =========================

  const loadOrders = async () => {

    const token = localStorage.getItem("token");

    try {

      const response = await fetch(
        `http://localhost:8089/api/orders/user/${userId}`,
        {
          headers: {
            "Authorization": `Bearer ${token}`
          }
        }
      );


      const data = await response.json();


      if (!response.ok) {

        setError(
          data.message ||
          "Unable to load orders"
        );

        return;

      }


      setOrders(data);

    } catch (error) {

      console.error(
        "Orders Error:",
        error
      );

      setError(
        "Unable to connect to server"
      );

    } finally {

      setLoading(false);

    }

  };


  // =========================
  // LOAD ORDERS ON PAGE OPEN
  // =========================

  useEffect(() => {

    loadOrders();

  }, []);


  // =========================
  // CANCEL ORDER
  // =========================

  const cancelOrder = async (orderId) => {

    const confirmCancel = window.confirm(
      "Are you sure you want to cancel this order?"
    );


    if (!confirmCancel) {

      return;

    }


    const token = localStorage.getItem("token");

    setMessage("");
    setError("");


    try {

      const response = await fetch(
        `http://localhost:8089/api/orders/${orderId}`,
        {
          method: "PUT",

          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },

          body: JSON.stringify({
            userId: userId,
            status: "CANCELLED"
          })

        }
      );


      const data = await response.json();


      if (!response.ok) {

        setError(
          data.message ||
          "Unable to cancel order"
        );

        return;

      }


      setMessage(
        "Order cancelled successfully."
      );


      loadOrders();

    } catch (error) {

      console.error(
        "Cancel Order Error:",
        error
      );

      setError(
        "Unable to connect to server"
      );

    }

  };


  // =========================
  // LOADING
  // =========================

  if (loading) {

    return (

      <div className="orders-page">

        <button
          className="back-button"
          onClick={onBack}
        >
          ← Back
        </button>


        <h2>
          📦 My Orders
        </h2>


        <p style={{ textAlign: "center" }}>
          Loading orders...
        </p>

      </div>

    );

  }


  // =========================
  // ORDERS PAGE
  // =========================

  return (

    <div className="orders-page">


      {/* =========================
          BACK BUTTON
      ========================= */}

      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back
      </button>


      {/* =========================
          TITLE
      ========================= */}

      <h2>
        📦 My Orders
      </h2>


      {/* =========================
          SUCCESS MESSAGE
      ========================= */}

      {message && (

        <p
          style={{
            textAlign: "center",
            color: "#188038",
            fontWeight: "bold",
            marginBottom: "20px"
          }}
        >
          ✅ {message}
        </p>

      )}


      {/* =========================
          ERROR MESSAGE
      ========================= */}

      {error && (

        <p
          style={{
            textAlign: "center",
            color: "#d93025",
            fontWeight: "bold",
            marginBottom: "20px"
          }}
        >
          ❌ {error}
        </p>

      )}


      {/* =========================
          NO ORDERS
      ========================= */}

      {orders.length === 0 ? (

        <div className="card">

          <h3>
            No Orders Found
          </h3>

          <p>
            You have not placed any orders yet.
          </p>

        </div>

      ) : (


        /* =========================
           ORDERS GRID
           ========================= */

        <div className="orders-grid">

          {orders.map((order) => (

            <div
              className="order-card"
              key={order.id}
            >


              {/* =========================
                  ORDER NUMBER
              ========================= */}

              <h3>
                📦 Order #{order.id}
              </h3>


              {/* =========================
                  TOTAL AMOUNT
              ========================= */}

              <p>

                <strong>
                  Total Amount:
                </strong>{" "}

                ₹{order.totalAmount}

              </p>


              {/* =========================
                  STATUS
              ========================= */}

              <p>

                <strong>
                  Status:
                </strong>{" "}

                <span className="order-status">

                  {order.status}

                </span>

              </p>


              {/* =========================
                  ACTION BUTTONS
              ========================= */}

              <div className="order-actions">


                {/* VIEW DETAILS */}

                <button
                  onClick={() =>
                    onViewDetails(order)
                  }
                >
                  View Details 👁️
                </button>


                {/* PAY NOW + CANCEL */}

                {order.status !== "CANCELLED" &&
                  order.status !== "DELIVERED" && (

                  <>

                    <button
                      onClick={() =>
                        onPay(order)
                      }
                    >
                      Pay Now 💳
                    </button>


                    <button
                      className="cancel-button"
                      onClick={() =>
                        cancelOrder(order.id)
                      }
                    >
                      Cancel ❌
                    </button>

                  </>

                )}

              </div>

            </div>

          ))}

        </div>

      )}

    </div>

  );

}


export default Orders;
