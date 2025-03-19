import React, { useEffect, useState } from 'react';

function SystemAdminHomePage() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch users from the backend API
    const fetchUsers = async () => {
        try {
            const response = await fetch('/api/sysadmin/users/all', {
                method: 'GET',
                headers: {
                    'X-API-KEY': 'guest',
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error('Failed to fetch users');
            }

            const data = await response.json();
            setUsers(data);
            setLoading(false);
        } catch (error) {
            setError(error.message);
            setLoading(false);
        }
    };

    // useEffect should be called directly in the component, not inside a callback
    useEffect(() => {
        fetchUsers();  // Call the fetchUsers function inside useEffect
    }, []); // Empty dependency array to fetch data only once when the component mounts

    // Show loading state
    if (loading) {
        return <div>Loading users...</div>;
    }

    // Show error if fetching fails
    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <h1>System Admin Home</h1>
            <h2>User List</h2>
            <table>
                <thead>
                <tr>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Last Login</th>
                </tr>
                </thead>
                <tbody>
                {users.map((user) => (
                    <tr key={user.username}>
                        <td>{user.username}</td>
                        <td>{user.first_name}</td>
                        <td>{user.last_name}</td>
                        <td>{user.email}</td>
                        <td>{user.last_login}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default SystemAdminHomePage;
