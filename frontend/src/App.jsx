
import { useState } from "react";
import "./App.css";

import Login from "./Login";
import MenuItems from "./MenuItems";
import Cart from "./Cart";
import Orders from "./Orders";
import Payment from "./Payment";
import Payments from "./Payments";
import Home from "./Home";
import Restaurants from "./Restaurants";
import OrderDetails from "./OrderDetails";
import AdminOrders from "./AdminOrders";


function App() {

  // =========================
  // LOGIN STATE
  // =========================

  const [loggedIn, setLoggedIn] = useState(
    !!localStorage.getItem("token")
  );


  // =========================
  // SELECTED RESTAURANT
  // =========================

  const [selectedRestaurant, setSelectedRestaurant] =
    useState(null);


  // =========================
  // PAGE STATES
  // =========================

  const [showCart, setShowCart] =
    useState(false);

  const [showOrders, setShowOrders] =
    useState(false);

  const [showPayments, setShowPayments] =
    useState(false);

  const [showAdminOrders, setShowAdminOrders] =
    useState(false);


  // =========================
  // ORDER STATES
  // =========================

  const [selectedOrder, setSelectedOrder] =
    useState(null);

  const [selectedOrderDetails, setSelectedOrderDetails] =
    useState(null);


  // =========================
  // GET USER ROLE FROM JWT
  // =========================

  const token = localStorage.getItem("token");

  let userRole = "";


  if (token) {

    try {

      const payload = JSON.parse(
        atob(token.split(".")[1])
      );

      userRole = payload.role || "";

      console.log(
        "User Role:",
        userRole
      );

    } catch (error) {

      console.error(
        "Unable to read JWT role"
      );

    }

  }


  // =========================
  // LOGOUT
  // =========================

  const handleLogout = () => {

    localStorage.removeItem("token");

    setLoggedIn(false);

    setSelectedRestaurant(null);

    setShowCart(false);

    setShowOrders(false);

    setShowPayments(false);

    setShowAdminOrders(false);

    setSelectedOrder(null);

    setSelectedOrderDetails(null);

  };


  // =========================
  // GO TO HOME
  // =========================

  const handleHome = (event) => {

    event.preventDefault();

    setSelectedRestaurant(null);

    setShowCart(false);

    setShowOrders(false);

    setShowPayments(false);

    setShowAdminOrders(false);

    setSelectedOrder(null);

    setSelectedOrderDetails(null);

    window.scrollTo({
      top: 0,
      behavior: "smooth"
    });

  };


  // =========================
  // GO TO RESTAURANTS
  // =========================

  const handleRestaurants = (event) => {

    event.preventDefault();

    setSelectedRestaurant(null);

    setShowCart(false);

    setShowOrders(false);

    setShowPayments(false);

    setShowAdminOrders(false);

    setSelectedOrder(null);

    setSelectedOrderDetails(null);

    setTimeout(() => {

      document
        .getElementById("restaurants")
        ?.scrollIntoView({
          behavior: "smooth"
        });

    }, 100);

  };


  // =========================
  // LOGIN CHECK
  // =========================

  if (!loggedIn) {

    return (
      <Login />
    );

  }


  // =========================
  // CART PAGE
  // =========================

  if (showCart) {

    return (
      <Cart
        onBack={() => {

          setShowCart(false);

        }}
      />
    );

  }


  // =========================
  // PAYMENT PAGE
  // =========================

  if (selectedOrder) {

    return (
      <Payment

        order={selectedOrder}

        onBack={() => {

          setSelectedOrder(null);

        }}

      />
    );

  }


  // =========================
  // ORDER DETAILS PAGE
  // =========================

  if (selectedOrderDetails) {

    return (
      <OrderDetails

        order={selectedOrderDetails}

        onBack={() => {

          setSelectedOrderDetails(null);

        }}

      />
    );

  }


  // =========================
  // ADMIN ORDERS PAGE
  // =========================

  if (showAdminOrders) {

    return (
      <AdminOrders

        onBack={() => {

          setShowAdminOrders(false);

        }}

      />
    );

  }


  // =========================
  // ORDERS PAGE
  // =========================

  if (showOrders) {

    return (
      <Orders

        onBack={() => {

          setShowOrders(false);

        }}

        onPay={(order) => {

          setSelectedOrder(order);

        }}

        onViewDetails={(order) => {

          setSelectedOrderDetails(order);

        }}

      />
    );

  }


  // =========================
  // PAYMENT HISTORY PAGE
  // =========================

  if (showPayments) {

    return (
      <Payments

        onBack={() => {

          setShowPayments(false);

        }}

      />
    );

  }


  // =========================
  // MENU PAGE
  // =========================

  if (selectedRestaurant) {

    return (
      <MenuItems

        restaurantId={
          selectedRestaurant.id
        }

        restaurantName={
          selectedRestaurant.name
        }

        onBack={() => {

          setSelectedRestaurant(null);

        }}

      />
    );

  }


  // =========================
  // MAIN HOME PAGE
  // =========================

  return (

    <div>

      {/* =========================
          NAVBAR
      ========================= */}

      <nav className="navbar">


        {/* =========================
            LOGO
        ========================= */}

        <div className="logo">

          🍔 QuickBite

        </div>


        {/* =========================
            NAVIGATION LINKS
        ========================= */}

        <div className="nav-links">


          {/* HOME */}

          <a
            href="#"
            onClick={handleHome}
          >
            Home
          </a>


          {/* RESTAURANTS */}

          <a
            href="#restaurants"
            onClick={handleRestaurants}
          >
            Restaurants
          </a>


          {/* CART */}

          <button
            onClick={() => {

              setShowCart(true);

              setShowOrders(false);
              setShowPayments(false);
              setShowAdminOrders(false);
              setSelectedRestaurant(null);
              setSelectedOrder(null);
              setSelectedOrderDetails(null);

            }}
          >
            Cart 🛒
          </button>


          {/* MY ORDERS */}

          <button
            onClick={() => {

              setShowOrders(true);

              setShowCart(false);
              setShowPayments(false);
              setShowAdminOrders(false);
              setSelectedRestaurant(null);
              setSelectedOrder(null);
              setSelectedOrderDetails(null);

            }}
          >
            My Orders 📦
          </button>


          {/* PAYMENT HISTORY */}

          <button
            onClick={() => {

              setShowPayments(true);

              setShowCart(false);
              setShowOrders(false);
              setShowAdminOrders(false);
              setSelectedRestaurant(null);
              setSelectedOrder(null);
              setSelectedOrderDetails(null);

            }}
          >
            Payment History 💳
          </button>


          {/* =========================
              ADMIN ONLY
          ========================= */}

          {userRole === "ADMIN" && (

            <button
              onClick={() => {

                setShowAdminOrders(true);

                setShowCart(false);
                setShowOrders(false);
                setShowPayments(false);
                setSelectedRestaurant(null);
                setSelectedOrder(null);
                setSelectedOrderDetails(null);

              }}
            >
              Admin Orders 🛠️
            </button>

          )}


          {/* =========================
              LOGOUT - LAST
          ========================= */}

          <button
            className="logout-button"
            onClick={handleLogout}
          >
            Logout
          </button>


        </div>

      </nav>


      {/* =========================
          HOME COMPONENT
      ========================= */}

      <Home />


      {/* =========================
          RESTAURANTS COMPONENT
      ========================= */}

      <Restaurants

        onSelectRestaurant={(restaurant) => {

          setSelectedRestaurant(
            restaurant
          );

        }}

      />


      {/* =========================
          FOOTER
      ========================= */}

      <footer className="footer">

        <p>

          © 2026 QuickBite.
          All rights reserved.

        </p>

      </footer>

    </div>

  );

}


export default App;
