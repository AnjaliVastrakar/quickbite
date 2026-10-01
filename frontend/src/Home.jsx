
function Home() {

  const exploreRestaurants = () => {

    document
      .getElementById("restaurants")
      ?.scrollIntoView({
        behavior: "smooth"
      });

  };


  return (

    <section className="hero">

      <div className="hero-content">

        {/* FOOD ICON */}

        <div className="hero-icon">
          🍔
        </div>


        {/* BRAND */}

        <h1>
          QuickBite
        </h1>


        {/* MAIN HEADING */}

        <h2>
          Delicious food,
          <br />
          delivered fast! 🚀
        </h2>


        {/* DESCRIPTION */}

        <p>
          Discover delicious meals from your favorite
          restaurants and get them delivered right to
          your doorstep.
        </p>


        {/* BUTTON */}

        <button
          onClick={exploreRestaurants}
          className="hero-button"
        >
          Explore Restaurants 🍽️
        </button>


        {/* SMALL FEATURES */}

        <div className="hero-features">

          <div className="hero-feature">
            <span>🍴</span>
            <p>Great Food</p>
          </div>

          <div className="hero-feature">
            <span>⚡</span>
            <p>Fast Delivery</p>
          </div>

          <div className="hero-feature">
            <span>❤️</span>
            <p>Easy Ordering</p>
          </div>

        </div>

      </div>

    </section>
  );
}

export default Home;
