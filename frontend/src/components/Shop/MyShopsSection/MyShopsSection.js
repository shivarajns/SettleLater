import { useEffect, useState } from "react";
import "./MyShopsSection.css";
import { Plus, MapPin, Store } from "lucide-react";

import CreateShopModal from "../CreateShopModal/CreateShopModal";
import { getAllShops } from "../ShopService";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function MyShopsSection() {

  const navigate = useNavigate();
  const [shops, setShops] = useState([]);
  const [loading, setLoading] = useState(true);

  const [selectedShop, setSelectedShop] =
    useState(null);

  const [showModal, setShowModal] =
    useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  const loadShops = async () => {
    try {
      setLoading(true);

      const data =
        await getAllShops();

      setShops(data);

      if (
        data &&
        data.length > 0
      ) {
        setSelectedShop(
          data[0].shopId
        );
      }
    } catch (error) {
      console.log("Error Object:", error);
      if (!error?.response?.data?.validToken) {
        handleLogout();
      }

    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadShops();
  }, []);

  return (
    <>
      <section className="shops-section">

        {/* Header */}

        <div className="shops-header">

          <div>
            <h2>
              My Shops
            </h2>

            <p>
              Manage and
              switch between
              your shops
            </p>
          </div>

          <button
            className="add-shop-btn"
            onClick={() =>
              setShowModal(
                true
              )
            }
          >
            <Plus size={18} />
            Add Shop
          </button>

        </div>

        {/* Loading */}

        {loading && (
          <div className="shops-grid">

            {[1, 2, 3].map(
              (item) => (
                <div
                  key={item}
                  className="shop-card skeleton-card"
                />
              )
            )}

          </div>
        )}

        {/* Empty State */}

        {!loading &&
          shops.length === 0 && (
            <div className="empty-state">

              <Store
                size={52}
              />

              <h3>
                No Shops Found
              </h3>

              <p>
                Create your
                first shop to
                start managing
                credits.
              </p>

              <button
                className="empty-add-btn"
                onClick={() =>
                  setShowModal(
                    true
                  )
                }
              >
                Create Shop
              </button>

            </div>
          )}

        {/* Shops */}

        {!loading &&
          shops.length > 0 && (
            <div className="shops-grid">

              {shops.map(
                (shop) => (
                  <div
                    key={
                      shop.shopId
                    }
                    className={`shop-card ${selectedShop ===
                      shop.shopId
                      ? "active"
                      : ""
                      }`}
                    onClick={() =>
                      setSelectedShop(
                        shop.shopId
                      )
                    }
                  >

                    <div className="shop-top">

                      <div className="shop-icon">
                        <Store
                          size={
                            20
                          }
                        />
                      </div>

                      {selectedShop ===
                        shop.shopId && (
                          <span className="active-badge">
                            Active
                          </span>
                        )}

                    </div>

                    <h3>
                      {shop.name}
                    </h3>

                    <span className="business-type">
                      {
                        shop.businessType
                      }
                    </span>

                    <div className="shop-location">

                      <MapPin
                        size={
                          14
                        }
                      />

                      <span>
                        {shop.city}
                        ,{" "}
                        {
                          shop.state
                        }
                      </span>

                    </div>

                    <div className="shop-footer">

                      <span>
                        {
                          shop.currency
                        }
                      </span>

                      <span
                        className={`status ${shop.active
                          ? "online"
                          : "offline"
                          }`}
                      >
                        {shop.active
                          ? "Active"
                          : "Inactive"}
                      </span>

                    </div>

                  </div>
                )
              )}

              {/* Add Shop Card */}

              <div
                className="shop-card add-card"
                onClick={() =>
                  setShowModal(
                    true
                  )
                }
              >

                <Plus size={28} />

                <span>
                  Add New Shop
                </span>

              </div>

            </div>
          )}

      </section>

      <CreateShopModal
        isOpen={showModal}
        onClose={() =>
          setShowModal(false)
        }
        onShopCreated={
          loadShops
        }
      />
    </>
  );
}

export default MyShopsSection;