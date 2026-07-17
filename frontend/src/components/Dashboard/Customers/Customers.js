import { useEffect, useState } from "react";
import "./Customers.css";

import {
  Users,
  Search,
  Plus,
  Phone,
  Mail,
  Store,
  Eye,
  MoreVertical,
  ChevronLeft,
  ChevronRight,
} from "lucide-react";

import { useNavigate } from "react-router-dom";

import { getAllCustomers } from "./CustomerService";
import CreateCustomerModal from "./CreateCustomerModal/CreateCustomerModal";

function Customers() {
  const navigate = useNavigate();

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);

  const [search, setSearch] = useState("");

  const [currentPage, setCurrentPage] =
    useState(0);

  const [totalPages, setTotalPages] =
    useState(0);

  const [totalElements, setTotalElements] =
    useState(0);

  const [hasNext, setHasNext] =
    useState(false);

  const [hasPrevious, setHasPrevious] =
    useState(false);

  const [showModal, setShowModal] =
    useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/sessionexpired");
  };

  const loadCustomers = async (
    page = 0
  ) => {
    try {
      setLoading(true);

      const response =
        await getAllCustomers(
          page,
          10
        );

      const data =
        response?.data;

      setCustomers(
        data?.customers || []
      );

      setCurrentPage(
        data?.currentPage || 0
      );

      setTotalPages(
        data?.totalPages || 0
      );

      setTotalElements(
        data?.totalElements || 0
      );

      setHasNext(
        data?.hasNext || false
      );

      setHasPrevious(
        data?.hasPrevious || false
      );

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
    loadCustomers(0);
  }, []);

  const filteredCustomers =
    customers.filter(
      (customer) => {
        const query =
          search.toLowerCase();

        return (
          customer.customerName
            ?.toLowerCase()
            .includes(query) ||

          customer.phone
            ?.toLowerCase()
            .includes(query) ||

          (customer.email || "")
            .toLowerCase()
            .includes(query) ||

          customer.ShopName
            ?.toLowerCase()
            .includes(query)
        );
      }
    );

  const activeCustomers =
    customers.filter(
      (customer) =>
        customer.active
    ).length;

  const inactiveCustomers =
    customers.filter(
      (customer) =>
        !customer.active
    ).length;

  const totalShops =
    new Set(
      customers.map(
        (customer) =>
          customer.ShopName
      )
    ).size;

  return (
    <>
      <section className="customers-page">

        {/* HEADER */}

        <div className="customers-header">

          <div>

            <h1>
              Customers
            </h1>

            <p>
              Manage all your
              customers
            </p>

          </div>

          <button
            className="add-customer-btn"
            onClick={() =>
              setShowModal(true)
            }
          >
            <Plus size={18} />
            Add Customer
          </button>

        </div>

        {/* STATS */}

        <div className="customer-stats-grid">

          <div className="customer-stat-card">

            <div className="stat-icon blue">
              <Users size={20} />
            </div>

            <div>
              <span>
                Total Customers
              </span>

              <h3>
                {totalElements}
              </h3>
            </div>

          </div>

          <div className="customer-stat-card">

            <div className="stat-icon green">
              <Users size={20} />
            </div>

            <div>
              <span>
                Active Customers
              </span>

              <h3>
                {activeCustomers}
              </h3>
            </div>

          </div>

          <div className="customer-stat-card">

            <div className="stat-icon orange">
              <Users size={20} />
            </div>

            <div>
              <span>
                Inactive Customers
              </span>

              <h3>
                {inactiveCustomers}
              </h3>
            </div>

          </div>

          <div className="customer-stat-card">

            <div className="stat-icon purple">
              <Store size={20} />
            </div>

            <div>
              <span>
                Shops Covered
              </span>

              <h3>
                {totalShops}
              </h3>
            </div>

          </div>

        </div>

        {/* SEARCH */}

        <div className="customer-search-section">

          <div className="customer-search-box">

            <Search size={18} />

            <input
              type="text"
              placeholder="Search customers by name, phone, email or shop..."
              value={search}
              onChange={(e) =>
                setSearch(
                  e.target.value
                )
              }
            />

          </div>

        </div>

        {/* LOADING */}

        {loading && (

          <div className="customer-table-skeleton">

            {[1, 2, 3, 4].map(
              (item) => (
                <div
                  key={item}
                  className="customer-skeleton-row"
                />
              )
            )}

          </div>

        )}

        {/* EMPTY */}

        {!loading &&
          customers.length === 0 && (

            <div className="empty-state">

              <Users size={52} />

              <h3>
                No Customers Found
              </h3>

              <p>
                Start by adding
                your first customer.
              </p>

              <button
                className="add-customer-btn"
                onClick={() =>
                  setShowModal(true)
                }
              >
                <Plus size={18} />
                Add Customer
              </button>

            </div>

          )}

        {/* TABLE */}

        {!loading &&
          filteredCustomers.length >
            0 && (

            <div className="customers-table-wrapper">

              <table className="customers-table">

                <thead>

                  <tr>

                    <th>
                      Customer
                    </th>

                    <th>
                      Shop
                    </th>

                    <th>
                      Phone
                    </th>

                    <th>
                      Email
                    </th>

                    <th>
                      Status
                    </th>

                    <th>
                      Actions
                    </th>

                  </tr>

                </thead>

                <tbody>

                  {filteredCustomers.map(
                    (customer) => (

                      <tr
                        key={
                          customer.customerId
                        }
                      >

                        <td>

                          <div className="customer-cell">

                            <div className="customer-avatar">

                              {customer.customerName
                                ?.charAt(0)
                                ?.toUpperCase()}

                            </div>

                            <div>

                              <h4>
                                {
                                  customer.customerName
                                }
                              </h4>

                              <span>
                                {new Date(
                                  customer.createdAt
                                ).toLocaleDateString()}
                              </span>

                            </div>

                          </div>

                        </td>

                        <td>

                          <div className="shop-name">

                            <Store size={14} />

                            {
                              customer.ShopName
                            }

                          </div>

                        </td>

                        <td>

                          <div className="table-meta">

                            <Phone size={14} />

                            {
                              customer.phone
                            }

                          </div>

                        </td>

                        <td>

                          {customer.email ? (

                            <div className="table-meta">

                              <Mail size={14} />

                              {
                                customer.email
                              }

                            </div>

                          ) : (
                            "-"
                          )}

                        </td>

                        <td>

                          <span
                            className={`customer-status ${
                              customer.active
                                ? "active"
                                : "inactive"
                            }`}
                          >
                            {customer.active
                              ? "Active"
                              : "Inactive"}
                          </span>

                        </td>

                        <td>

                          <div className="action-buttons">

                            <button
                              className="view-btn"
                              type="button"
                            >
                              <Eye size={16} />
                              View
                            </button>

                            <button
                              className="more-btn"
                            >
                              <MoreVertical
                                size={16}
                              />
                            </button>

                          </div>

                        </td>

                      </tr>
                    )
                  )}

                </tbody>

              </table>

            </div>

          )}

        {/* PAGINATION */}

        {!loading &&
          totalPages > 1 && (

            <div className="pagination">

              <button
                disabled={
                  !hasPrevious
                }
                onClick={() =>
                  loadCustomers(
                    currentPage - 1
                  )
                }
              >
                <ChevronLeft
                  size={16}
                />
              </button>

              <span>
                Page{" "}
                {currentPage + 1}
                {" "}of{" "}
                {totalPages}
              </span>

              <button
                disabled={
                  !hasNext
                }
                onClick={() =>
                  loadCustomers(
                    currentPage + 1
                  )
                }
              >
                <ChevronRight
                  size={16}
                />
              </button>

            </div>

          )}

      </section>

      <CreateCustomerModal
        isOpen={showModal}
        onClose={() =>
          setShowModal(false)
        }
        onCustomerCreated={() =>
          loadCustomers(currentPage)
        }
      />
    </>
  );
}

export default Customers;