
import DashNavbar from "../../components/Dashboard/DashNavbar"
import Sidebar from "../../components/Sidebar/Sidebar"
import DashboardLayout from "../../layouts/DashboardLayout/DashboardLayout"
import MyShopsSection from "../../components/Shop/MyShopsSection/MyShopsSection"

function Dashboard() {
    return (
        <DashboardLayout>
            <MyShopsSection/>
        </DashboardLayout>
    )
}

export default Dashboard