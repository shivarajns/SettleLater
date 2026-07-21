import {
    createContext,
    useContext,
    useEffect,
    useState
} from "react";

import { getCurrentUser } from "../api/authApi";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [user, setUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        const initializeAuth = async () => {

            const token = localStorage.getItem("token");

            if (!token) {
                setLoading(false);
                return;
            }

            try {

                const response = await getCurrentUser();
                setUser(response.data);
                setIsAuthenticated(true);

            } catch (error) {

                localStorage.removeItem("token");

                setUser(null);
                setIsAuthenticated(false);

            } finally {
                setLoading(false);
            }
        };

        initializeAuth();

    }, []);

    const logout = () => {
        localStorage.removeItem("token");
        setUser(null);
        setIsAuthenticated(false);
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                isAuthenticated,
                loading,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};