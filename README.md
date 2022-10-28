# githubtrendapi

This api running on http://44.202.117.91:8080/githubTrends/trendrepos

This API is basically a system that pulls popular repositories from https://github.com/trending

Uses jsoup as crawler library.

## Initialization

Set server port on src > main > resources > application.properties then run.

## Using

Get request to 

*your-host-name*:*your-port*/trendrepos

For example => http://localhost:8080/trendrepos 

## Response

```javascript
[
...
  {
    "id": "9ba1a11b4da57c250cea01059f8e6733",
    "name": "stable-diffusion-webui",
    "owner": "AUTOMATIC1111",
    "full_name": "AUTOMATIC1111/stable-diffusion-webui",
    "description": "Stable Diffusion web UI",
    "language": "Python",
    "totalstars": 8685,
    "forks": 1212,
    "todays_stars": 828
  },
...
]
```
