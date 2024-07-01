import "./App.css";
import { Box } from "@chakra-ui/react";
import { Navbar } from "./component/Navbar";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Footer } from "./component/Footer";
import { Roles } from "./view/Roles";
import { Units } from "./view/Units";
import { UserRoles } from "./view/UserRoles";
import { Users } from "./view/Users";
import { Home } from "./view/Home";

function App() {
  return (
    <Router>
      <Box className="App">
        <Navbar />
        <Box className="content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/users" element={<Users />} />
            <Route path="/units" element={<Units />} />
            <Route path="/roles" element={<Roles />} />
            <Route path="/user-roles" element={<UserRoles />} />
          </Routes>
        </Box>
        <Footer />
      </Box>
    </Router>
  );
}

export default App;
