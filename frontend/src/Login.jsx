
import { useState } from "react";

function Login() {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);


  // =========================
  // LOGIN
  // =========================

  const handleLogin = async (e) => {

    e.preventDefault();

    setMessage("");
    setLoading(true);

    try {

      const response = await fetch(
        "http://localhost:8089/api/auth/login",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json"
          },

          body: JSON.stringify({
            email: email,
            password: password
          })
        }
      );

      const data = await response.json();

      console.log(
        "Login response:",
        data
      );


      // =========================
      // LOGIN FAILED
      // =========================

      if (!response.ok) {

        setMessage(
          data.message ||
          "Invalid email or password"
        );

        return;
      }


      // =========================
      // SAVE JWT TOKEN
      // =========================

      localStorage.setItem(
        "token",
        data.token
      );

      console.log(
        "JWT saved successfully"
      );


      setMessage(
        "Login successful! 🎉"
      );


      // =========================
      // REFRESH APP
      // =========================

      setTimeout(() => {

        window.location.reload();

      }, 500);


    } catch (error) {

      console.error(
        "Login error:",
        error
      );

      setMessage(
        "Unable to connect to backend"
      );

    } finally {

      setLoading(false);
    }
  };


  return (

    <div className="login-page">

      <div className="login-container">

        {/* =========================
            BRAND
        ========================= */}

        <div className="login-brand">

          <div className="login-logo">
            🍔
          </div>

          <h1>
            QuickBite
          </h1>

          <p>
            Delicious food, delivered fast!
          </p>

        </div>


        {/* =========================
            LOGIN CARD
        ========================= */}

        <div className="login-card">

          <div className="login-welcome">

            <h2>
              Welcome Back 👋
            </h2>

            <p>
              Login to continue ordering your
              favorite food.
            </p>

          </div>


          {/* =========================
              LOGIN FORM
          ========================= */}

          <form onSubmit={handleLogin}>

            {/* EMAIL */}

            <div className="login-field">

              <label htmlFor="email">
                Email Address
              </label>

              <div className="login-input-wrapper">

                <span>
                  ✉️
                </span>

                <input
                  id="email"
                  type="email"
                  value={email}
                  onChange={(e) =>
                    setEmail(e.target.value)
                  }
                  placeholder="Enter your email"
                  required
                />

              </div>

            </div>


            {/* PASSWORD */}

            <div className="login-field">

              <label htmlFor="password">
                Password
              </label>

              <div className="login-input-wrapper">

                <span>
                  🔒
                </span>

                <input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) =>
                    setPassword(e.target.value)
                  }
                  placeholder="Enter your password"
                  required
                />

              </div>

            </div>


            {/* LOGIN BUTTON */}

            <button
              type="submit"
              className="login-button"
              disabled={loading}
            >

              {loading
                ? "Logging in..."
                : "Login 🔐"
              }

            </button>

          </form>


          {/* =========================
              MESSAGE
          ========================= */}

          {message && (

            <p
              className={
                message.includes("successful")
                  ? "login-success"
                  : "login-error"
              }
            >
              {message}
            </p>

          )}

        </div>


        {/* =========================
            FOOTER
        ========================= */}

        <p className="login-footer">
          © 2026 QuickBite
          <span> • </span>
          Fresh food. Fast delivery.
        </p>

      </div>

    </div>
  );
}

export default Login;
