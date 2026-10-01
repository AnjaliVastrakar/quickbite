
const API_BASE_URL = "http://localhost:8089/api";

// =========================
// GET ALL RESTAURANTS
// =========================

export const getRestaurants = async () => {

  const token = localStorage.getItem("token");

  const response = await fetch(
    `${API_BASE_URL}/restaurants`,
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
      `Failed to fetch restaurants: ${response.status}`
    );
  }

  return response.json();
};


// =========================
// SEARCH RESTAURANTS
// =========================

export const searchRestaurants = async (keyword) => {

  const token = localStorage.getItem("token");

  const response = await fetch(
    `${API_BASE_URL}/restaurants/search?keyword=${encodeURIComponent(keyword)}`,
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
      `Failed to search restaurants: ${response.status}`
    );
  }

  return response.json();
};


export default API_BASE_URL;
