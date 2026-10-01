
import { useEffect, useState } from "react";

function Payments({ onBack }) {

  const [payments, setPayments] = useState([]);
  const [error, setError] = useState("");

  const userId = 21;

  // =========================
  // LOAD PAYMENT HISTORY
  // =========================

  useEffect(() => {

    const token = localStorage.getItem("token");

    fetch(
      `http://localhost:8089/api/payments/user/${userId}`,
      {
        method: "GET",

        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        }
      }
    )
      .then((response) => {

        if (!response.ok) {
          throw new Error(
            `Failed to load payments: ${response.status}`
          );
        }

        return response.json();
      })

      .then((data) => {

        console.log("Payments:", data);

        setPayments(data);
      })

      .catch((error) => {

        console.error(
          "Payment Error:",
          error
        );

        setError(
          "Unable to load payment history"
        );
      });

  }, []);


  // =========================
  // PAYMENT HISTORY UI
  // =========================

  return (

    <div className="payments-page">

      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back
      </button>


      <h2>
        💳 Payment History
      </h2>


      {error && (
        <div className="payments-error">
          ❌ {error}
        </div>
      )}


      {payments.length === 0 && !error && (

        <div className="payments-empty">

          <div className="payments-empty-icon">
            💳
          </div>

          <h3>
            No Payments Found
          </h3>

          <p>
            You have not made any payments yet.
          </p>

        </div>

      )}


      {payments.length > 0 && (

        <div className="payments-grid">

          {payments.map((payment) => (

            <div
              className="payment-history-card"
              key={payment.id}
            >

              <div className="payment-history-header">

                <h3>
                  💳 Payment #{payment.id}
                </h3>

                <span className="payment-status">
                  {payment.paymentStatus}
                </span>

              </div>


              <div className="payment-history-info">

                <p>
                  <strong>
                    Order ID
                  </strong>

                  <span>
                    #{payment.orderId}
                  </span>
                </p>


                <p>
                  <strong>
                    Amount
                  </strong>

                  <span className="payment-amount">
                    ₹{payment.amount}
                  </span>
                </p>


                <p>
                  <strong>
                    Payment Method
                  </strong>

                  <span>
                    {payment.paymentMethod}
                  </span>
                </p>


                <p>
                  <strong>
                    Status
                  </strong>

                  <span>
                    {payment.paymentStatus}
                  </span>
                </p>

              </div>

            </div>

          ))}

        </div>

      )}

    </div>

  );
}

export default Payments;
