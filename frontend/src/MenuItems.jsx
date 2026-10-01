import { useEffect, useState } from "react";

function MenuItems({ restaurantId, restaurantName, onBack }) {

  const [menuItems, setMenuItems] = useState([]);
  const [allMenuItems, setAllMenuItems] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);


  // =========================
  // LOAD MENU ITEMS
  // =========================

  const loadMenuItems = async () => {

    const token = localStorage.getItem("token");
    const id = Number(restaurantId);

    console.log("================================");
    console.log("Restaurant Name:", restaurantName);
    console.log("Restaurant ID received:", restaurantId);
    console.log("Restaurant ID converted:", id);
    console.log("Token available:", !!token);
    console.log("================================");

    if (!restaurantId || Number.isNaN(id)) {

      setError("Restaurant ID is missing");
      setMenuItems([]);
      setAllMenuItems([]);

      return;
    }

    setLoading(true);
    setError("");
    setMessage("");

    try {

      const url =
        `http://localhost:8089/api/menu-items/restaurant/${id}`;

      const response = await fetch(
        url,
        {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`
          }
        }
      );

      console.log("Menu API URL:", url);
      console.log("Menu API Status:", response.status);

      const responseText =
        await response.text();

      console.log(
        "Menu API Response:",
        responseText
      );

      if (!response.ok) {

        let errorMessage =
          `Failed to load menu. HTTP ${response.status}`;

        if (responseText.trim()) {

          try {

            const errorData =
              JSON.parse(responseText);

            errorMessage =
              errorData.message ||
              errorData.error ||
              errorMessage;

          } catch {

            errorMessage =
              responseText ||
              errorMessage;
          }
        }

        throw new Error(errorMessage);
      }

      if (!responseText.trim()) {

        setMenuItems([]);
        setAllMenuItems([]);

        setError(
          "Server returned an empty menu response"
        );

        return;
      }

      let data;

      try {

        data =
          JSON.parse(responseText);

      } catch (parseError) {

        console.error(
          "JSON Parse Error:",
          parseError
        );

        throw new Error(
          "Server returned invalid menu data"
        );
      }

      console.log(
        "Restaurant Menu:",
        data
      );

      if (!Array.isArray(data)) {

        throw new Error(
          "Invalid menu data received from server"
        );
      }

      setAllMenuItems(data);
      setMenuItems(data);
      setError("");

    } catch (error) {

      console.error(
        "Menu API Error:",
        error
      );

      setMenuItems([]);
      setAllMenuItems([]);

      setError(
        error.message ||
        "Unable to load menu items"
      );

    } finally {

      setLoading(false);
    }
  };


  // =========================
  // LOAD MENU WHEN RESTAURANT CHANGES
  // =========================

  useEffect(() => {

    setSearchKeyword("");

    loadMenuItems();

  }, [restaurantId]);


  // =========================
  // SEARCH MENU ITEMS
  // =========================

  const handleSearch = () => {

    const keyword =
      searchKeyword.trim().toLowerCase();

    setError("");

    if (keyword === "") {

      setMenuItems(allMenuItems);

      return;
    }

    const filteredItems =
      allMenuItems.filter((item) => {

        const name =
          item.name?.toLowerCase() || "";

        const category =
          item.category?.toLowerCase() || "";

        const description =
          item.description?.toLowerCase() || "";

        return (
          name.includes(keyword) ||
          category.includes(keyword) ||
          description.includes(keyword)
        );
      });

    setMenuItems(filteredItems);
  };


  // =========================
  // RESET SEARCH
  // =========================

  const handleReset = () => {

    setSearchKeyword("");
    setError("");
    setMenuItems(allMenuItems);
  };


  // =========================
  // ADD TO CART
  // =========================

  const addToCart = async (item) => {

    const token =
      localStorage.getItem("token");

    const userId = 21;

    console.log(
      "Adding item to cart:",
      item
    );

    try {

      const response = await fetch(
        "http://localhost:8089/api/cart",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },

          body: JSON.stringify({

            userId: userId,
            menuItemId: item.id,
            quantity: 1,
            price: item.price

          })
        }
      );

      const responseText =
        await response.text();

      let data = null;

      if (responseText.trim()) {

        try {

          data =
            JSON.parse(responseText);

        } catch {

          console.log(
            "Cart response is not JSON:",
            responseText
          );
        }
      }

      console.log(
        "Cart API Status:",
        response.status
      );

      console.log(
        "Cart Response:",
        data
      );

      if (!response.ok) {

        throw new Error(
          data?.message ||
          responseText ||
          "Failed to add item to cart"
        );
      }

      setMessage(
        `${item.name} added to cart 🛒`
      );

      setTimeout(() => {
        setMessage("");
      }, 2000);

    } catch (error) {

      console.error(
        "Cart Error:",
        error
      );

      setMessage(
        error.message ||
        "Unable to add item to cart"
      );
    }
  };


  // =========================
  // UI
  // =========================

  return (

    <div className="menu-page">

      {/* BACK TO RESTAURANTS */}

      <button
        className="back-button"
        onClick={onBack}
      >
        ← Back to Restaurants
      </button>


      {/* RESTAURANT HEADER */}

      <div className="menu-header">

        <div className="menu-header-icon">
          🍽️
        </div>

        <h2>
          {restaurantName} Menu
        </h2>

        <p>
          Choose your favorite dishes
        </p>

      </div>


      {/* SEARCH */}

      <div className="menu-search-box">

        <input
          type="text"
          value={searchKeyword}
          onChange={(e) =>
            setSearchKeyword(e.target.value)
          }
          placeholder="Search menu item or category"
        />

        <button onClick={handleSearch}>
          🔎 Search
        </button>

        <button
          className="menu-reset-button"
          onClick={handleReset}
        >
          Reset
        </button>

      </div>


      {/* LOADING */}

      {loading && (
        <div className="menu-message">
          🍽️ Loading menu items...
        </div>
      )}


      {/* ERROR */}

      {error && !loading && (
        <div className="menu-error">
          ❌ {error}
        </div>
      )}


      {/* SUCCESS */}

      {message && (
        <div className="menu-success">
          ✅ {message}
        </div>
      )}


      {/* NO ITEMS */}

      {!loading &&
        !error &&
        menuItems.length === 0 && (

          <div className="menu-empty">

            <div className="menu-empty-icon">
              🍽️
            </div>

            <h3>
              No Menu Items Found
            </h3>

            <p>
              Try another search or check back later.
            </p>

          </div>
        )}


      {/* MENU CARDS */}

      {!loading &&
        !error &&
        menuItems.length > 0 && (

          <div className="menu-grid">

            {menuItems.map((item) => (

              <div
                className="menu-card"
                key={item.id}
              >

                <div className="menu-card-icon">
                  🍔
                </div>

                <h3>
                  {item.name}
                </h3>

                <p className="menu-description">
                  {item.description}
                </p>


                <div className="menu-item-details">

                  <p>
                    <strong>
                      Category
                    </strong>

                    <span>
                      {item.category}
                    </span>
                  </p>


                  <p>
                    <strong>
                      Price
                    </strong>

                    <span className="menu-price">
                      ₹{item.price}
                    </span>
                  </p>


                  <p>
                    <strong>
                      Availability
                    </strong>

                    <span>
                      {item.available
                        ? "✅ Available"
                        : "❌ Not Available"}
                    </span>
                  </p>

                </div>


                {item.available && (

                  <button
                    className="add-cart-button"
                    onClick={() =>
                      addToCart(item)
                    }
                  >
                    Add to Cart 🛒
                  </button>

                )}


                {!item.available && (

                  <button
                    className="not-available-button"
                    disabled
                  >
                    Not Available
                  </button>

                )}

              </div>

            ))}

          </div>
        )}

    </div>
  );
}

export default MenuItems;