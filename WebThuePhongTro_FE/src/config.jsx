export const loadConfig = async () => {
  try {
    const response = await fetch('/config.json');
    if (!response.ok) throw new Error('Config load failed');
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Could not load config:', error);
    return {};
  }
};
