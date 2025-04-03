import { useState } from 'preact/hooks'
import './app.css'

export function App() {
    let Login = "";

    return (
    <>
        <div className={Login}>
            <p id="email-tag">E-mail:</p>
            <input type="text" id="email" name="email"/>
            <br/>
            <p id={"password-tag"}>Password:</p>
            <input type="password" id="password" name="password"/>
            <br/>
            <br/>
            <input type={"submit"} />
        </div>
    </>
    )
}
