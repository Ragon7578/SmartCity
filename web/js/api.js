/** Urban Lens API client — talks to the Java backend service */
window.UrbanLensApi = (function () {
  const API_BASE = window.URBAN_LENS_API_BASE || "/api/v1";

  async function getJson(path) {
    const response = await fetch(API_BASE + path, {
      headers: { Accept: "application/json" },
    });
    if (!response.ok) {
      const body = await response.json().catch(() => ({}));
      const message = body.message || response.statusText || "Request failed";
      throw new Error(message);
    }
    return response.json();
  }

  return {
    getScene(domain) {
      const query = domain ? `?domain=${encodeURIComponent(domain)}` : "";
      return getJson("/city/scene" + query);
    },
    getAsset(id) {
      return getJson("/assets/" + encodeURIComponent(id));
    },
    getSystemInfo() {
      return getJson("/system/info");
    },
  };
})();
