import { useState } from 'preact/hooks'
import './app.css'

export function App() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();

        const response = await fetch("http://localhost:8080/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        });

        const data = await response.json();
        if (response.ok) {
            alert("Login Successful: " + data.message);
            setEmail("");
            setPassword("");
        } else {
            alert("Login Failed: " + data.error);
        }
    };

    return (
    <>
        <h2>Välkommen, granne! </h2>
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <label htmlFor="email">E-mail:</label>
                <input
                    type="text"
                    id="email"
                    name="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <br />
                <br />

                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <br />
                <br />

                <input type="submit" value="Login" />
            </form>
        </div>
    </>
    )
}
