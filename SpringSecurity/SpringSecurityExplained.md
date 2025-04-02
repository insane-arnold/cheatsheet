# Sping Security
## JWT Authentication Flow in Spring Security: From Client Request to Protected Resource

```plaintext
Client Request (with JWT)
        ↓
SecurityFilterChain (starts here)
        ↓
JwtAuthenticationFilter (validates JWT)
        ↓
Extracts Username from JWT
        ↓
UserDetailsService.loadUserByUsername(username) ← **(Fetches user from DB)**
        ↓
Creates UsernamePasswordAuthenticationToken (for Spring Security)
        ↓
AuthenticationManager (delegates to)
        ↓
AuthenticationProvider (validates user)
        ↓
Authenticated Token Returned
        ↓
SecurityContextHolder Updated (user is authenticated)
        ↓
Authorization Check (roles, permissions)
        ↓
Controller Handles Request
        ↓
Response Sent Back to Client
```


