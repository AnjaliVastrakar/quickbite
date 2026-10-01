
import { useEffect, useState } from "react";

import {
  getRestaurants,
  searchRestaurants
} from "./services/api";

function Restaurants({ onSelectRestaurant }) {

  const [restaurants, setRestaurants] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);


  // =========================
  // LOAD RESTAURANTS
  // =========================

  const loadRestaurants = async () => {

    setLoading(true);
    setError("");

    try {

      const data = await getRestaurants();

      setRestaurants(data);

    } catch (error) {

      console.error(
        "Restaurant Load Error:",
        error
      );

      setError(
        "Unable to load restaurants"
      );

    } finally {

      setLoading(false);

    }
  };


  // =========================
  // LOAD ON PAGE OPEN
  // =========================

  useEffect(() => {

    loadRestaurants();

  }, []);


  // =========================
  // SEARCH RESTAURANTS
  // =========================

  const handleSearch = async () => {

    setError("");

    const keyword =
      searchKeyword.trim();

    if (keyword === "") {

      loadRestaurants();

      return;
    }

    try {

      setLoading(true);

      const data =
        await searchRestaurants(keyword);

      setRestaurants(data);

    } catch (error) {

      console.error(
        "Restaurant Search Error:",
        error
      );

      setError(
        "Unable to search restaurants"
      );

    } finally {

      setLoading(false);

    }
  };


  // =========================
  // RESET SEARCH
  // =========================

  const handleReset = () => {

    setSearchKeyword("");

    loadRestaurants();

  };


  return (

    <section
      className="restaurants-page"
      id="restaurants"
    >

      {/* =========================
          HEADER
          ========================= */}

      <div className="restaurants-header">

        <div className="restaurants-icon">
          🍽️
        </div>

        <h2>
          Explore Our Restaurants
        </h2>

        <p>
          Find your favorite food from our
          selection of restaurants.
        </p>

      </div>


      {/* =========================
          SEARCH
          ========================= */}

      <div className="restaurant-search-box">

        <input
          type="text"
          value={searchKeyword}
          onChange={(e) =>
            setSearchKeyword(e.target.value)
          }
          placeholder="Search restaurant, location or cuisine"
        />

        <button
          onClick={handleSearch}
        >
          🔎 Search
        </button>

        <button
          className="restaurant-reset-button"
          onClick={handleReset}
        >
          Reset
        </button>

      </div>


      {/* =========================
          LOADING
          ========================= */}

      {loading && (

        <div className="restaurant-message">
          🍽️ Loading restaurants...
        </div>

      )}


      {/* =========================
          ERROR
          ========================= */}

      {error && !loading && (

        <div className="restaurant-error">
          ❌ {error}
        </div>

      )}


      {/* =========================
          EMPTY
          ========================= */}

      {!loading &&
        !error &&
        restaurants.length === 0 && (

        <div className="restaurant-empty">

          <div className="restaurant-empty-icon">
            🍽️
          </div>

          <h3>
            No Restaurants Found
          </h3>

          <p>
            Try searching for another restaurant,
            location or cuisine.
          </p>

        </div>

      )}


      {/* =========================
          RESTAURANT CARDS
          ========================= */}

      {!loading &&
        !error &&
        restaurants.length > 0 && (

        <div className="restaurant-grid">

          {restaurants.map(
            (restaurant) => (

            <div
              className="restaurant-card"
              key={restaurant.id}
            >

              {/* CARD ICON */}

              <div className="restaurant-card-icon">
                🍴
              </div>


              {/* RESTAURANT NAME */}

              <h3>
                {restaurant.name}
              </h3>


              {/* CUISINE */}

              <div className="restaurant-info">

                <div className="restaurant-info-row">

                  <span>
                    🍜
                  </span>

                  <div>
                    <small>
                      Cuisine
                    </small>

                    <strong>
                      {restaurant.cuisine}
                    </strong>
                  </div>

                </div>


                {/* LOCATION */}

                <div className="restaurant-info-row">

                  <span>
                    📍
                  </span>

                  <div>
                    <small>
                      Location
                    </small>

                    <strong>
                      {restaurant.location}
                    </strong>
                  </div>

                </div>

              </div>


              {/* VIEW MENU */}

              <button
                className="view-menu-button"
                onClick={() =>
                  onSelectRestaurant(
                    restaurant
                  )
                }
              >
                View Menu 🍽️
              </button>

            </div>

          ))}

        </div>

      )}

    </section>
  );
}

export default Restaurants;
