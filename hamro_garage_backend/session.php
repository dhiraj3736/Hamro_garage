<?php
// Start the session
session_start();

// Retrieve the session ID
$sessionId = session_id();

// Output the session ID as a response header
header("Session-ID: $sessionId");
?>