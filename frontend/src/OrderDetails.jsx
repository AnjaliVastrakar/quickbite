
function OrderDetails({ order, onBack }) {

  const status = order.status;

  const isPlaced =
    status === "PLACED" ||
    status === "PREPARING" ||
    status === "OUT_FOR_DELIVERY" ||
    status === "DELIVERED";

  const isPreparing =
    status === "PREPARING" ||
    status === "OUT_FOR_DELIVERY" ||
    status === "DELIVERED";

  const isOutForDelivery =
    status === "OUT_FOR_DELIVERY" ||
    status === "DELIVERED";

  const isDelivered =
    status === "DELIVERED";

  const isCancelled =
    status === "CANCELLED";


  return (
    <div className="order-details-page">

      {/* Back Button */}
      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back to Orders
      </button>


      {/* Page Heading */}
      <h2>📦 Order Details</h2>


      {/* Order Details Card */}
      <div className="order-details-card">

        <div className="order-details-header">

          <h3>
            📦 Order #{order.id}
          </h3>

          <span className="order-status">
            {status}
          </span>

        </div>


        <div className="order-info">

          <p>
            <strong>User ID:</strong>
            <span>{order.userId}</span>
          </p>

          <p>
            <strong>Total Amount:</strong>
            <span>₹{order.totalAmount}</span>
          </p>

          <p>
            <strong>Order Status:</strong>
            <span>{status}</span>
          </p>

        </div>


        <hr />


        {/* CANCELLED */}
        {isCancelled ? (

          <div className="cancelled-order">

            <div className="cancelled-icon">
              ❌
            </div>

            <h3>
              Order Cancelled
            </h3>

            <p>
              This order has been cancelled.
            </p>

          </div>

        ) : (

          <>

            {/* TRACKING */}
            <h3 className="tracking-title">
              🚚 Order Tracking
            </h3>


            <div className="tracking">


              {/* ORDER PLACED */}

              <div className="tracking-step">

                <div className="tracking-icon">
                  {isPlaced ? "✅" : "⚪"}
                </div>

                <div className="tracking-text">

                  <strong>
                    Order Placed
                  </strong>

                  <span>
                    Order has been placed successfully.
                  </span>

                </div>

              </div>


              <div className="tracking-line"></div>


              {/* PREPARING */}

              <div className="tracking-step">

                <div className="tracking-icon">
                  {isPreparing ? "✅" : "⚪"}
                </div>

                <div className="tracking-text">

                  <strong>
                    Preparing
                  </strong>

                  <span>
                    Restaurant is preparing your food.
                  </span>

                </div>

              </div>


              <div className="tracking-line"></div>


              {/* OUT FOR DELIVERY */}

              <div className="tracking-step">

                <div className="tracking-icon">
                  {isOutForDelivery ? "✅" : "⚪"}
                </div>

                <div className="tracking-text">

                  <strong>
                    Out for Delivery
                  </strong>

                  <span>
                    Your order is on the way.
                  </span>

                </div>

              </div>


              <div className="tracking-line"></div>


              {/* DELIVERED */}

              <div className="tracking-step">

                <div className="tracking-icon">
                  {isDelivered ? "✅" : "⚪"}
                </div>

                <div className="tracking-text">

                  <strong>
                    Delivered
                  </strong>

                  <span>
                    Your order has been delivered.
                  </span>

                </div>

              </div>

            </div>

          </>

        )}

      </div>

    </div>
  );
}


export default OrderDetails;
