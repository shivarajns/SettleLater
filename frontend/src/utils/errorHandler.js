export const getErrorMessage = (error) => {
  if (error.response) {
    return (
      error.response.data?.message ||
      "Request failed."
    );
  }

  if (error.request) {
    return "Server unreachable. Please try again.";
  }

  return "Something went wrong.";
};