import './App.css'
import AuthPage from './Login-Singup/AuthPage'
import Home from './Dashboard/home'
import React from "react";
import AddCash from './Dashboard/addCash';
import Dashboard from './Dashboard/dashboard'
import UserDashboard from './Dashboard/userDashboard'
import MFAPage from './Login-Singup/mfa'
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AdminDashboard from './AdminDashboard/Admindashboard';
import AdminHome from './AdminDashboard/adminHome';
import AdminDashboardUsers from './AdminDashboard/Users/users'
function App() {

  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<AuthPage />} />
          <Route path="/mfapage" element={<MFAPage />} />

          {/*Making the dashboard option open like a dashnoard element not like a new page logic START*/}
          <Route path="/dashboard" element={<Dashboard />}>
            <Route path="home" element={<Home />} />
            <Route path="addCash" element={<AddCash />} />
            <Route path="userDashboard" element={<UserDashboard />} />
            {/*Making the dashboard option open like a dashnoard element not like a new page logic END*/}
          </Route>
          {/*Making the dashboard option open like a dashnoard element not like a new page logic START*/}
          <Route path="/admindashboard" element={<AdminDashboard />}>
            <Route path="adminHome" element={<AdminHome />} />
            <Route path="adminDashboardUsers" element={<AdminDashboardUsers/>} />
            {/*Making the dashboard option open like a dashnoard element not like a new page logic END*/}
          </Route>
        </Routes>
      </Router>
    </>
  )
}

export default App
