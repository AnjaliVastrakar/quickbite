
import { useEffect, useState } from "react";

function AdminOrders({ onBack }) {

  const [orders, setOrders] = useState([]);
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  // =========================
  // LOAD ALL ORDERS
  // =========================

  const loadOrders = async () => {

    const token = localStorage.getItem("token");

    try {

      const response = await fetch(
        "http://localhost:8089/api/orders",
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
          `Failed to load orders: ${response.status}`
        );
      }

      const data = await response.json();

      console.log("Admin Orders:", data);

      setOrders(data);

    } catch (error) {

      console.error("Admin Order Error:", error);

      setError("Unable to load orders");
    }
  };


  useEffect(() => {
    loadOrders();
  }, []);


  // =========================
  // UPDATE ORDER STATUS
  // =========================

  const updateStatus = async (order, newStatus) => {

    const token = localStorage.getItem("token");

    setError("");
    setMessage("");

    try {

      const response = await fetch(
        `http://localhost:8089/api/orders/${order.id}`,
        {
          method: "PUT",

          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },

          body: JSON.stringify({
            userId: order.userId,
            totalAmount: order.totalAmount,
            status: newStatus
          })
        }
      );

      const data = await response.json();

      console.log("Updated Order:", data);

      if (!response.ok) {

        setError(
          data.message ||
          "Failed to update order"
        );

        return;
      }

      setMessage(
        `Order #${order.id} status updated to ${newStatus}`
      );

      loadOrders();

    } catch (error) {

      console.error(
        "Update Order Error:",
        error
      );

      setError(
        "Unable to update order"
      );
    }
  };


  // =========================
  // ADMIN ORDERS UI
  // =========================

  return (

    <div className="admin-orders-page">

      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back
      </button>


      <h2>
        🛠️ Admin Order Management
      </h2>


      {error && (
        <div className="admin-error">
          ❌ {error}
        </div>
      )}


      {message && (
        <div className="admin-success">
          ✅ {message}
        </div>
      )}


      {orders.length === 0 && !error && (
        <div className="admin-empty">
          <div className="admin-empty-icon">
            📦
          </div>

          <h3>
            No Orders Found
          </h3>

          <p>
            There are currently no customer orders.
          </p>
        </div>
      )}


      {orders.length > 0 && (

        <div className="admin-orders-grid">

          {orders.map((order) => (

            <div
              className="admin-order-card"
              key={order.id}
            >

              <div className="admin-order-header">

                <h3>
                  📦 Order #{order.id}
                </h3>

                <span className="admin-order-status">
                  {order.status}
                </span>

              </div>


              <div className="admin-order-info">

                <p>
                  <strong>
                    User ID
                  </strong>

                  <span>
                    {order.userId}
                  </span>
                </p>


                <p>
                  <strong>
                    Amount
                  </strong>

                  <span>
                    ₹{order.totalAmount}
                  </span>
                </p>


                <p>
                  <strong>
                    Current Status
                  </strong>

                  <span>
                    {order.status}
                  </span>
                </p>

              </div>


              <div className="admin-status-section">

                <label>
                  <strong>
                    🔄 Update Order Status
                  </strong>
                </label>


                <select
                  value={order.status}
                  onChange={(e) =>
                    updateStatus(
                      order,
                      e.target.value
                    )
                  }
                >

                  <option value="PLACED">
                    PLACED
                  </option>

                  <option value="PREPARING">
                    PREPARING
                  </option>

                  <option value="OUT_FOR_DELIVERY">
                    OUT FOR DELIVERY
                  </option>

                  <option value="DELIVERED">
                    DELIVERED
                  </option>

                  <option value="CANCELLED">
                    CANCELLED
                  </option>

                </select>

              </div>

            </div>

          ))}

        </div>

      )}

    </div>
  );
}

export default AdminOrders;
