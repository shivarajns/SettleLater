import { useEffect, useState } from "react";
import "./ShopsSection.css";

import {
  Plus,
  Store,
  Search,
  MapPin,
  Phone,
  Mail,
  Eye,
  MoreVertical,
} from "lucide-react";

import CreateShopModal from "../Dashboard/CreateShopModal/CreateShopModal"
import { getAllShops } from "../Dashboard/ShopService";
import { useNavigate } from "react-router-dom";

function ShopsSection() {
  const navigate = useNavigate();

  const [shops, setShops] = useState([]);
  const [loading, setLoading] = useState(true);

  const [search, setSearch] = useState("");

  const [showModal, setShowModal] =
    useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/sessionexpired");
  };

  const loadShops = async () => {
    try {
      setLoading(true);

      const data =
        await getAllShops();

      setShops(data);
    } catch (error) {
      console.log(error);

      if (
        !error?.response?.data
          ?.validToken
      ) {
        handleLogout();
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadShops();
  }, []);

  const filteredShops =
    shops.filter((shop) => {
      const query =
        search.toLowerCase();

      return (
        shop.name
          ?.toLowerCase()
          .includes(query) ||
        shop.city
          ?.toLowerCase()
          .includes(query) ||
        shop.state
          ?.toLowerCase()
          .includes(query) ||
        shop.shopId
          ?.toLowerCase()
          .includes(query) ||
        shop.businessType
          ?.toLowerCase()
          .includes(query)
      );
    });

  return (
    <>
      <section className="shops-page">

        {/* Header */}

        <div className="shops-page-header">

          <div>
            <h1>My Shops</h1>

            <p>
              Manage all your shops
              from one place
            </p>
          </div>

          <button
            className="add-shop-btn"
            onClick={() =>
              setShowModal(true)
            }
          >
            <Plus size={18} />
            Add New Shop
          </button>

        </div>

        {/* Stats */}

        <div className="stats-grid">

          <div className="stat-card">
            <div className="stat-icon blue">
              <Store size={20} />
            </div>

            <div>
              <span>
                Total Shops
              </span>

              <h3>
                {shops.length}
              </h3>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon green">
              <Store size={20} />
            </div>

            <div>
              <span>
                Total Customers
              </span>

              <h3>0</h3>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon orange">
              <Store size={20} />
            </div>

            <div>
              <span>
                Outstanding
              </span>

              <h3>₹0</h3>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon purple">
              <Store size={20} />
            </div>

            <div>
              <span>
                Overdue Amount
              </span>

              <h3>₹0</h3>
            </div>
          </div>

        </div>

        {/* Search */}

        <div className="search-section">

          <div className="search-box">
            <Search size={18} />

            <input
              type="text"
              placeholder="Search shops by name, city, state or shop ID..."
              value={search}
              onChange={(e) =>
                setSearch(
                  e.target.value
                )
              }
            />
          </div>

        </div>

        {/* Loading */}

        {loading && (
          <div className="shop-list">

            {[1, 2, 3].map(
              (item) => (
                <div
                  key={item}
                  className="shop-skeleton"
                />
              )
            )}

          </div>
        )}

        {/* Empty State */}

        {!loading &&
          shops.length === 0 && (
            <div className="empty-state">

              <Store size={52} />

              <h3>
                No Shops Found
              </h3>

              <p>
                Create your first
                shop to start
                managing credits.
              </p>

              <button
                className="empty-btn"
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

        {/* Shop List */}

        {!loading &&
          filteredShops.length >
            0 && (
            <div className="shop-list">

              {filteredShops.map(
                (shop) => (
                  <div
                    key={
                      shop.shopId
                    }
                    className="shop-row-card"
                  >
                    <div className="shop-row-left">

                      <div className="shop-avatar">
                        <Store size={22} />
                      </div>

                      <div className="shop-details">

                        <div className="shop-title-row">

                          <h3>
                            {
                              shop.name
                            }
                          </h3>

                          <span
                            className={`shop-status ${
                              shop.active
                                ? "active"
                                : "inactive"
                            }`}
                          >
                            {shop.active
                              ? "Active"
                              : "Inactive"}
                          </span>

                        </div>

                        <p className="business-type">
                          {
                            shop.businessType
                          }
                        </p>

                        <span className="shop-id">
                          {
                            shop.shopId
                          }
                        </span>

                        <div className="shop-meta">

                          <div className="meta-item">
                            <MapPin
                              size={
                                14
                              }
                            />
                            <span>
                              {
                                shop.city
                              }
                              ,
                              {" "}
                              {
                                shop.state
                              }
                            </span>
                          </div>

                          <div className="meta-item">
                            <Phone
                              size={
                                14
                              }
                            />
                            <span>
                              {
                                shop.phoneNumber
                              }
                            </span>
                          </div>

                          <div className="meta-item">
                            <Mail
                              size={
                                14
                              }
                            />
                            <span>
                              {
                                shop.email
                              }
                            </span>
                          </div>

                        </div>

                      </div>

                    </div>

                    <div className="shop-row-actions">

                      <button
                        className="view-btn"
                        disabled
                      >
                        <Eye
                          size={
                            16
                          }
                        />
                        View
                      </button>

                      <button className="more-btn">
                        <MoreVertical
                          size={
                            18
                          }
                        />
                      </button>

                    </div>

                  </div>
                )
              )}

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

export default ShopsSection;