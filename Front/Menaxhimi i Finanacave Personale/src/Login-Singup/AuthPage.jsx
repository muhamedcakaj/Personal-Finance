import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const navigate = useNavigate();

  const toggleForm = () => {
    setIsLogin(!isLogin);
  };

  //Login Logic Start//
  const handleLogin = async (e) => {
    e.preventDefault();
    const userName = e.target.userName.value;
    const password = e.target.password.value;

    try {
      const response = await fetch("http://localhost:8083/v1/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ userName, password }),
      });

      if (response.ok) {
        alert("You logged in sucesfully");
        sessionStorage.setItem('userName', userName);
        navigate("/mfapage");
      } else {
        const error = await response.text();
        alert(error); // Display error message
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };
  //Login Logic End//


  //Singup Logic Start//
  const handleSingup = async (e) => {
    e.preventDefault();

    const name = e.target.name.value;
    const surname = e.target.surname.value;
    const userName = e.target.userName.value;
    const password = e.target.password.value;
    const email = e.target.email.value;
    const role = "ROLE_USER"

    if (name.length > 25 || surname.length > 25) {
      alert('Name and surname should be less than 25 characters');
      return;
    }

    if (userName.length > 30) {
      alert('Username should be less than 30 characters');
      return;
    }

    if (password.length > 10) {
      alert('Password should be less than 10 characters');
      return;
    }

    try {
      const response = await fetch("http://localhost:8083/v1/user", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ name, surname, userName, password, email, role }),
      });
      if (response.ok) {

        //Creating a bilanc for the user logic START
        const bilanci = 0.0;
        const username = userName;
        const response2 = await fetch("http://localhost:8083/v1/bilanc", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ username, bilanci }),
        });
        //Creating a bilanc for the user logic END

        alert("You singed up sucesfully please login");
        window.location.reload();
      } else {
        const error = await response.text();
        alert(error); // Display error message
      }
    } catch (error) {
      console.error("Error:", error);
    }
  }

  //SingUp logic end//

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <div className="flex w-full max-w-4xl bg-white rounded-lg shadow-lg overflow-hidden">
        <div className="flex-1 p-8">
          <h2 className="text-2xl font-semibold text-gray-800 mb-2">
            {isLogin ? 'Welcome Back' : 'Create an Account'}
          </h2>
          <p className="text-gray-600 mb-6">
            {isLogin ? 'Hey, welcome back to your personal finance space!' : 'Sign up to start managing your finances!'}
          </p>

          {isLogin ? (
            <form className="space-y-4" onSubmit={handleLogin}>
              <input
                type="text"
                name="userName"
                placeholder="Username"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="password"
                name="password"
                placeholder="Password"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <div className="flex justify-between items-center text-sm text-gray-600">
                <label>
                  <input type="checkbox" className="mr-1" /> Remember me
                </label>
              </div>
              <button
                type="submit"
                className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
              >
                Sign In
              </button>
              <p className="text-sm text-center text-gray-600">
                Don't have an account?{' '}
                <span onClick={toggleForm} className="text-blue-500 cursor-pointer font-bold">
                  Sign Up
                </span>
              </p>
            </form>
          ) : (
            <form className="space-y-4" onSubmit={handleSingup}>
              <input
                type="text"
                name="name"
                placeholder="First Name"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="text"
                name="surname"
                placeholder="Last Name"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="text"
                name="userName"
                placeholder="Username"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="password"
                name="password"
                placeholder="Password"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <input
                type="email"
                name="email"
                placeholder="Email"
                required
                className="w-full px-4 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <button
                type="submit"
                className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
              >
                Sign Up
              </button>
              <p className="text-sm text-center text-gray-600">
                Already have an account?{' '}
                <span onClick={toggleForm} className="text-blue-500 cursor-pointer font-bold">
                  Login
                </span>
              </p>
            </form>
          )}
        </div>

        <div className="flex-1 hidden md:flex items-center justify-center bg-gradient-to-r from-blue-600 to-blue-300 text-white text-center">
          <div className="max-w-xs">
            <h3 className="text-lg font-bold mb-2">Your Finance, Simplified</h3>
            <p className="text-sm">
              Take control of your spending with ease.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuthPage;